package com.newhorizon_service.config;

import com.newhorizon_service.model.*;
import com.newhorizon_service.model.enums.CompetenceType;
import com.newhorizon_service.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final CompetenceRepository competenceRepository;
    private final CourseRepository courseRepository;
    private final PositionRepository positionRepository;
    private final TrailRepository trailRepository;
    private final UserRepository userRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            log.info("Iniciando população do banco de dados com dados seed...");
            
            // Limpa os dados existentes
            userRepository.deleteAll();
            trailRepository.deleteAll();
            positionRepository.deleteAll();
            courseRepository.deleteAll();
            competenceRepository.deleteAll();

            // 1. Criar Competências
            log.info("Criando competências...");
            Competence javaCompetence = new Competence();
            javaCompetence.setName("Java Programming");
            javaCompetence.setType(CompetenceType.HARD);

            Competence springCompetence = new Competence();
            springCompetence.setName("Spring Framework");
            springCompetence.setType(CompetenceType.HARD);

            Competence communicationCompetence = new Competence();
            communicationCompetence.setName("Comunicação Efetiva");
            communicationCompetence.setType(CompetenceType.SOFT);

            Competence teamworkCompetence = new Competence();
            teamworkCompetence.setName("Trabalho em Equipe");
            teamworkCompetence.setType(CompetenceType.SOFT);

            Competence sqlCompetence = new Competence();
            sqlCompetence.setName("SQL e Bancos de Dados");
            sqlCompetence.setType(CompetenceType.HARD);

            Competence leadershipCompetence = new Competence();
            leadershipCompetence.setName("Liderança");
            leadershipCompetence.setType(CompetenceType.SOFT);

            List<Competence> competences = competenceRepository.saveAll(Arrays.asList(
                    javaCompetence, springCompetence, communicationCompetence,
                    teamworkCompetence, sqlCompetence, leadershipCompetence
            ));
            log.info("Criadas {} competências", competences.size());

            // 2. Criar Cursos
            log.info("Criando cursos...");
            Course javaBasics = new Course();
            javaBasics.setName("Java Básico");

            Course javaAdvanced = new Course();
            javaAdvanced.setName("Java Avançado");

            Course springBoot = new Course();
            springBoot.setName("Spring Boot Fundamentals");

            Course microservices = new Course();
            microservices.setName("Arquitetura de Microserviços");

            Course database = new Course();
            database.setName("Banco de Dados Relacional");

            Course softSkills = new Course();
            softSkills.setName("Desenvolvimento de Soft Skills");

            List<Course> courses = courseRepository.saveAll(Arrays.asList(
                    javaBasics, javaAdvanced, springBoot, microservices, database, softSkills
            ));
            log.info("Criados {} cursos", courses.size());

            // 3. Criar Posições (primeiro sem relacionamentos)
            log.info("Criando posições...");
            Position juniorDev = new Position();
            juniorDev.setName("Desenvolvedor Java Júnior");

            Position pleno = new Position();
            pleno.setName("Desenvolvedor Java Pleno");

            Position senior = new Position();
            senior.setName("Desenvolvedor Java Sênior");

            Position techLead = new Position();
            techLead.setName("Tech Lead");

            // Salvar posições primeiro
            List<Position> positions = positionRepository.saveAll(Arrays.asList(
                    juniorDev, pleno, senior, techLead
            ));
            log.info("Criadas {} posições", positions.size());

            // Agora adicionar os relacionamentos
            juniorDev.setCompetences(Arrays.asList(javaCompetence, communicationCompetence));
            pleno.setCompetences(Arrays.asList(javaCompetence, springCompetence, sqlCompetence, teamworkCompetence));
            senior.setCompetences(Arrays.asList(javaCompetence, springCompetence, sqlCompetence, leadershipCompetence));
            techLead.setCompetences(Arrays.asList(
                    javaCompetence, springCompetence, sqlCompetence,
                    leadershipCompetence, communicationCompetence
            ));

            // Salvar novamente com os relacionamentos
            positionRepository.saveAll(Arrays.asList(juniorDev, pleno, senior, techLead));
            log.info("Relacionamentos das posições atualizados");

            // 4. Criar Trilhas (primeiro sem relacionamentos)
            log.info("Criando trilhas...");
            Trail beginnerTrail = new Trail();
            beginnerTrail.setName("Trilha Iniciante Java");

            Trail intermediateTrail = new Trail();
            intermediateTrail.setName("Trilha Intermediária Java");

            Trail advancedTrail = new Trail();
            advancedTrail.setName("Trilha Avançada Java");

            // Salvar trilhas primeiro
            List<Trail> trails = trailRepository.saveAll(Arrays.asList(
                    beginnerTrail, intermediateTrail, advancedTrail
            ));
            log.info("Criadas {} trilhas", trails.size());

            // Agora adicionar os relacionamentos
            beginnerTrail.setCourses(Arrays.asList(javaBasics, database, softSkills));
            beginnerTrail.setCompetences(Arrays.asList(javaCompetence, communicationCompetence, teamworkCompetence));
            beginnerTrail.setPositions(Arrays.asList(juniorDev));

            intermediateTrail.setCourses(Arrays.asList(javaAdvanced, springBoot, database));
            intermediateTrail.setCompetences(Arrays.asList(javaCompetence, springCompetence, sqlCompetence, teamworkCompetence));
            intermediateTrail.setPositions(Arrays.asList(pleno));

            advancedTrail.setCourses(Arrays.asList(microservices, springBoot, database, softSkills));
            advancedTrail.setCompetences(Arrays.asList(
                    javaCompetence, springCompetence, sqlCompetence, leadershipCompetence
            ));
            advancedTrail.setPositions(Arrays.asList(senior, techLead));

            // Salvar novamente com os relacionamentos
            trailRepository.saveAll(Arrays.asList(beginnerTrail, intermediateTrail, advancedTrail));
            log.info("Relacionamentos das trilhas atualizados");

            // 5. Criar Usuários (primeiro sem relacionamentos)
            log.info("Criando usuários...");
            User user1 = new User();
            user1.setName("Maria Silva");
            user1.setEmail("maria.silva@example.com");

            User user2 = new User();
            user2.setName("João Santos");
            user2.setEmail("joao.santos@example.com");

            User user3 = new User();
            user3.setName("Ana Costa");
            user3.setEmail("ana.costa@example.com");

            User user4 = new User();
            user4.setName("Pedro Oliveira");
            user4.setEmail("pedro.oliveira@example.com");

            // Salvar usuários primeiro
            List<User> users = userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));
            log.info("Criados {} usuários", users.size());

            // Agora adicionar os relacionamentos
            user1.setCompetences(Arrays.asList(javaCompetence, communicationCompetence));
            user1.setTrails(Arrays.asList(beginnerTrail));

            user2.setCompetences(Arrays.asList(javaCompetence, springCompetence, sqlCompetence, teamworkCompetence));
            user2.setTrails(Arrays.asList(intermediateTrail));

            user3.setCompetences(Arrays.asList(
                    javaCompetence, springCompetence, sqlCompetence, leadershipCompetence, communicationCompetence
            ));
            user3.setTrails(Arrays.asList(advancedTrail));

            user4.setCompetences(Arrays.asList(javaCompetence, teamworkCompetence));
            user4.setTrails(Arrays.asList(beginnerTrail, intermediateTrail));

            // Salvar novamente com os relacionamentos
            userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));
            log.info("Relacionamentos dos usuários atualizados");

            log.info("===== Dados seed criados com sucesso! =====");
            log.info("Total: {} competências, {} cursos, {} posições, {} trilhas, {} usuários",
                    competences.size(), courses.size(), positions.size(), trails.size(), users.size());
        };
    }
}
