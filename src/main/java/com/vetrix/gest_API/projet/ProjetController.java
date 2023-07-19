package com.vetrix.gest_API.projet;

import com.vetrix.gest_API.account.AccountRepository;
import com.vetrix.gest_API.account.Role;
import com.vetrix.gest_API.account.ServiceMail;
import com.vetrix.gest_API.technicien.TechRepository;
import com.vetrix.gest_API.technicien.Technicien;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/projet")
@Tag(name = "Projet")
@CrossOrigin("*")
public class ProjetController {
    @Autowired
    private ProjetRepository repository;
    @Autowired
    private TechRepository techRepository;
    @Autowired
    private ServiceMail serviceMail;
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping(path = "/create")
    public Projet createProjet(@RequestBody Projet projet){
        projet.setDate_creation(new Date());
        projet.setEtape(Etape.NEW);
        Projet p = repository.save(projet);
        serviceMail.sendSimpleEmail(
                accountRepository.findByRole(Role.CHARGE).getLogin(),
                "Assigné une équipe au projet "+p.getNumero(),
                "Bonsoir Monsieur le Chargé D'exploitation veuillez assigné des techniciens au projet "+p.getNumero()
        );
        return p;
    }
    @PutMapping(path = "/update")
    public Projet editNew(@RequestBody Projet projet, @RequestParam("numero") Integer numero){
        Projet proj = repository.findById(numero).get();
        proj.setVille(projet.getVille());
        proj.setQuartier(projet.getQuartier());
        proj.setGps(projet.getGps());
        proj.setDescription(projet.getDescription());
        proj.setType(projet.getType());

        return repository.save(proj);
    }

    @PutMapping(path = "/tech")
    public Projet addTech(@RequestParam("numero") Integer numero, @RequestBody List<Technicien> techniciens){
        Projet projet = repository.findById(numero).get();
        for (Technicien tech:techniciens){
            tech.setProjet(projet);
            techRepository.save(tech);
        }
        projet.setEtape(Etape.ASSIGNE);
        Projet p = repository.save(projet);
        serviceMail.sendSimpleEmail(
                accountRepository.findByRole(Role.ORDRE).getLogin(),
                "Donner la date des Travaux du projet "+p.getNumero(),
                "Monsieur le chargé de l'ordonnancement veullez donner la date des travaux du projet "+p.getNumero()
        );
        return p;
    }
    @PutMapping(path = "/programme")
    public Projet programme(@RequestParam("numero") Integer numero, @RequestBody Projet projet){
        Projet proj = repository.findById(numero).get();
        proj.setNum_service(projet.getNum_service());
        proj.setNum_accord(projet.getNum_accord());
        proj.setBon_commande(projet.getBon_commande());
        proj.setBon_achat(projet.getBon_achat());
        proj.setDate_travaux(projet.getDate_travaux());
        proj.setEtape(Etape.PROGRAMME);
        Projet p =  repository.save(proj);
        serviceMail.sendSimpleEmail(
                accountRepository.findByRole(Role.CHARGE).getLogin(),
                "Travaux du projet "+projet.getNumero() + " Programmé",
                "Monsieur le chargé d'exploitation, veuillez valider le programme"
        );
        return p;
    }
    @PutMapping(path = "/travaux_en_cours")
    public Projet travaux_en_cours(@RequestParam("numero") Integer numero){
        Projet proj = repository.findById(numero).get();
        proj.setEtape(Etape.COURS);
        Projet p =  repository.save(proj);
        for (Technicien technicien:proj.getTech()){
            serviceMail.sendSimpleEmail(
                    technicien.getEmail(),
                    "Travaux du projet "+p.getNumero() + " Programmé",
                    "Monsieur le Technicien, les travaux pour le projet "+ p.getNumero()+" ont été programmé pour le "+p.getDate_travaux()
            );
        }
        return p;
    }
    @PutMapping(path = "/travaux_term")
    public Projet travaux_termine(@RequestParam("numero") Integer numero, @RequestBody Projet projet){
        Projet proj = repository.findById(numero).get();
        proj.setDate_fin_travaux(projet.getDate_fin_travaux());
        proj.setEtape(Etape.TERM);
        Projet p = repository.save(proj);
        serviceMail.sendSimpleEmail(
                accountRepository.findByRole(Role.ORDRE).getLogin(),
                "Travaux du projet "+p.getNumero() + " Terminé",
                "Monsieur le chargé de l'ordonnancement veuillez engagé la procedure de payement pour le projet "+p.getNumero()
        );
        return p;
    }
    @PutMapping(path = "/terminer_projet")
    public Projet travaux_termine(@RequestParam("numero") Integer numero){
        Projet proj = repository.findById(numero).get();
        proj.setEtape(Etape.END);
        Projet p = repository.save(proj);
        serviceMail.sendSimpleEmail(
                accountRepository.findByRole(Role.CHEF).getLogin(),
                "Projet "+p.getNumero() + " Terminé",
                "Monsieur le chef d'exploitation le projet "+p.getNumero()+" est terminé"
        );
        return p;
    }
    @GetMapping(path = "/1")
    public List<Projet> find1(){
        return repository.findByEtape(Etape.NEW);
    }
    @GetMapping(path = "/2")
    public List<Projet> find2(){
        return repository.findByEtape(Etape.ASSIGNE);
    }
    @GetMapping(path = "/3")
    public List<Projet> find3(){
        return repository.findByEtape(Etape.PROGRAMME);
    }
    @GetMapping(path = "/4")
    public List<Projet> find4(){
        return repository.findByEtape(Etape.COURS);
    }
    @GetMapping(path = "/5")
    public List<Projet> find5(){
        return repository.findByEtape(Etape.TERM);
    }
    @GetMapping(path = "/6")
    public List<Projet> find6(){
        return repository.findByEtape(Etape.END);
    }

    @GetMapping(path = "/count")
    public ProjetsCount getAllCount(){
        return new ProjetsCount(
                repository.count_project(Etape.NEW),
                repository.count_project(Etape.ASSIGNE),
                repository.count_project(Etape.PROGRAMME),
                repository.count_project(Etape.COURS),
                repository.count_project(Etape.TERM),
                repository.count_project(Etape.END)
        );
    }
}
