package com.newhorizon_service.service;

import com.newhorizon_service.dto.analysis.*;
import com.newhorizon_service.dto.competence.GetSimpleCompetence;
import com.newhorizon_service.dto.position.GetSimplePosition;
import com.newhorizon_service.dto.trail.GetSimpleTrail;
import com.newhorizon_service.model.*;
import com.newhorizon_service.model.enums.CompetenceType;
import com.newhorizon_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final UserRepository userRepository;
    private final PositionRepository positionRepository;
    private final TrailRepository trailRepository;
    private final CompetenceRepository competenceRepository;
    private final ModelMapper modelMapper;

    /**
     * Analisa o fit do usuário com todas as posições disponíveis
     */
    public List<PositionFitDto> analyzeUserPositionFit(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        // Obter todas as competências do usuário através de suas posições e trilhas
        Set<Competence> userCompetences = getUserCompetences(user);

        List<Position> allPositions = positionRepository.findAll();
        
        return allPositions.stream()
                .map(position -> calculatePositionFit(position, userCompetences))
                .sorted(Comparator.comparing(PositionFitDto::getFitPercentage).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Obtém análise detalhada do perfil do usuário
     */
    public UserAnalysisDto getUserAnalysis(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Set<Competence> userCompetences = getUserCompetences(user);

        long hardSkills = userCompetences.stream()
                .filter(c -> c.getType() == CompetenceType.HARD)
                .count();

        long softSkills = userCompetences.stream()
                .filter(c -> c.getType() == CompetenceType.SOFT)
                .count();

        UserAnalysisDto analysis = new UserAnalysisDto();
        analysis.setUserId(user.getId());
        analysis.setUserName(user.getName());
        analysis.setEmail(user.getEmail());
        analysis.setCompetences(userCompetences.stream()
                .map(c -> modelMapper.map(c, GetSimpleCompetence.class))
                .collect(Collectors.toList()));
        analysis.setEnrolledTrails(user.getTrails().stream()
                .map(t -> modelMapper.map(t, GetSimpleTrail.class))
                .collect(Collectors.toList()));
        analysis.setTotalCompetences(userCompetences.size());
        analysis.setHardSkills((int) hardSkills);
        analysis.setSoftSkills((int) softSkills);

        return analysis;
    }

    /**
     * Recomenda trilhas para o usuário baseado em gaps de competências
     */
    public List<TrailRecommendationDto> recommendTrails(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Set<Competence> userCompetences = getUserCompetences(user);
        Set<String> enrolledTrailIds = user.getTrails().stream()
                .map(Trail::getId)
                .collect(Collectors.toSet());

        List<Trail> availableTrails = trailRepository.findAll().stream()
                .filter(trail -> !enrolledTrailIds.contains(trail.getId()))
                .collect(Collectors.toList());

        return availableTrails.stream()
                .map(trail -> calculateTrailRelevance(trail, userCompetences))
                .sorted(Comparator.comparing(TrailRecommendationDto::getRelevanceScore).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Lista posições compatíveis com uma trilha específica
     */
    public PositionsByTrailDto getPositionsByTrail(String trailId) {
        Trail trail = trailRepository.findById(trailId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trilha não encontrada"));

        // Obter todas as competências da trilha através de seus cursos
        // Note: Course não tem relação direta com Competence no modelo atual
        Set<Competence> trailCompetences = new HashSet<>();

        List<Position> allPositions = positionRepository.findAll();

        List<PositionMatchDto> matchingPositions = allPositions.stream()
                .map(position -> calculatePositionMatch(position, trailCompetences))
                .filter(match -> match.getMatchingCompetences() > 0)
                .sorted(Comparator.comparing(PositionMatchDto::getMatchingCompetences).reversed())
                .collect(Collectors.toList());

        PositionsByTrailDto result = new PositionsByTrailDto();
        result.setTrailId(trail.getId());
        result.setTrailName(trail.getName());
        result.setMatchingPositions(matchingPositions);
        result.setTotalPositions(matchingPositions.size());

        return result;
    }

    /**
     * Busca usuários que têm fit com uma posição específica
     */
    public List<UserAnalysisDto> getUsersByPositionFit(String positionId, Double minFitPercentage) {
        Position position = positionRepository.findById(positionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Posição não encontrada"));

        List<User> allUsers = userRepository.findAll();

        return allUsers.stream()
                .map(user -> {
                    Set<Competence> userCompetences = getUserCompetences(user);
                    PositionFitDto fit = calculatePositionFit(position, userCompetences);
                    
                    if (fit.getFitPercentage() >= minFitPercentage) {
                        return getUserAnalysis(user.getId());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // ===== MÉTODOS AUXILIARES =====

    /**
     * Obtém todas as competências do usuário (de posições e trilhas)
     */
    private Set<Competence> getUserCompetences(User user) {
        Set<Competence> competences = new HashSet<>();

        // Competências diretas do usuário
        if (user.getCompetences() != null) {
            competences.addAll(user.getCompetences());
        }

        // Note: No modelo atual, as competências vêm diretamente da relação User->Competence

        return competences;
    }

    /**
     * Calcula o fit entre uma posição e as competências do usuário
     */
    private PositionFitDto calculatePositionFit(Position position, Set<Competence> userCompetences) {
        Set<Competence> requiredCompetences = new HashSet<>(position.getCompetences());
        
        Set<String> userCompetenceIds = userCompetences.stream()
                .map(Competence::getId)
                .collect(Collectors.toSet());

        List<String> matching = new ArrayList<>();
        List<String> missing = new ArrayList<>();

        requiredCompetences.forEach(competence -> {
            if (userCompetenceIds.contains(competence.getId())) {
                matching.add(competence.getName());
            } else {
                missing.add(competence.getName());
            }
        });

        double fitPercentage = requiredCompetences.isEmpty() ? 0.0 : 
                (matching.size() * 100.0) / requiredCompetences.size();

        PositionFitDto fit = new PositionFitDto();
        fit.setPositionId(position.getId());
        fit.setPositionName(position.getName());
        fit.setFitPercentage(Math.round(fitPercentage * 100.0) / 100.0);
        fit.setMatchingCompetences(matching);
        fit.setMissingCompetences(missing);
        fit.setTotalRequiredCompetences(requiredCompetences.size());
        fit.setUserCompetences(matching.size());

        return fit;
    }

    /**
     * Calcula a relevância de uma trilha para o usuário
     */
    private TrailRecommendationDto calculateTrailRelevance(Trail trail, Set<Competence> userCompetences) {
        // Competências que a trilha oferece
        // Note: No modelo atual, não há relação Course->Competence
        Set<Competence> trailCompetences = new HashSet<>();

        Set<String> userCompetenceIds = userCompetences.stream()
                .map(Competence::getId)
                .collect(Collectors.toSet());

        // Competências novas que o usuário ganharia
        List<String> newCompetences = trailCompetences.stream()
                .filter(c -> !userCompetenceIds.contains(c.getId()))
                .map(Competence::getName)
                .collect(Collectors.toList());

        // Posições relacionadas que usam essas competências
        List<Position> relatedPositions = positionRepository.findAll().stream()
                .filter(position -> position.getCompetences().stream()
                        .anyMatch(trailCompetences::contains))
                .collect(Collectors.toList());

        double relevanceScore = newCompetences.size() * 10.0 + relatedPositions.size() * 5.0;

        String recommendation = generateRecommendation(newCompetences.size(), relatedPositions.size());

        TrailRecommendationDto dto = new TrailRecommendationDto();
        dto.setTrailId(trail.getId());
        dto.setTrailName(trail.getName());
        dto.setRelevanceScore(relevanceScore);
        dto.setCompetencesGained(newCompetences);
        dto.setRelatedPositions(relatedPositions.stream()
                .map(Position::getName)
                .collect(Collectors.toList()));
        dto.setRecommendation(recommendation);

        return dto;
    }

    /**
     * Calcula match entre posição e competências da trilha
     */
    private PositionMatchDto calculatePositionMatch(Position position, Set<Competence> trailCompetences) {
        Set<String> trailCompetenceIds = trailCompetences.stream()
                .map(Competence::getId)
                .collect(Collectors.toSet());

        List<String> matchingNames = position.getCompetences().stream()
                .filter(c -> trailCompetenceIds.contains(c.getId()))
                .map(Competence::getName)
                .collect(Collectors.toList());

        PositionMatchDto match = new PositionMatchDto();
        match.setPositionId(position.getId());
        match.setPositionName(position.getName());
        match.setMatchingCompetences(matchingNames.size());
        match.setCompetenceNames(matchingNames);

        return match;
    }

    /**
     * Gera recomendação textual baseada nos dados
     */
    private String generateRecommendation(int newCompetences, int relatedPositions) {
        if (newCompetences >= 5 && relatedPositions >= 3) {
            return "Altamente recomendado! Esta trilha oferece muitas competências novas e abre portas para diversas posições.";
        } else if (newCompetences >= 3) {
            return "Recomendado. Você aprenderá competências valiosas que podem impulsionar sua carreira.";
        } else if (relatedPositions >= 2) {
            return "Interessante. Esta trilha está alinhada com posições disponíveis no mercado.";
        } else if (newCompetences > 0) {
            return "Pode ser útil. Adiciona algumas competências ao seu perfil.";
        } else {
            return "Você já possui a maioria das competências desta trilha.";
        }
    }
}
