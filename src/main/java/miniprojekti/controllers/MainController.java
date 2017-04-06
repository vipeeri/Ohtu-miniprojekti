/*
 */
package miniprojekti.controllers;

import java.util.ArrayList;
import java.util.List;
import miniprojekti.entities.RefField;
import miniprojekti.entities.Reference;
import miniprojekti.entities.Type;
import miniprojekti.repositories.ReferenceRepository;
import miniprojekti.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    ReferenceRepository refRepo;

    @RequestMapping(value = "/references", method = RequestMethod.GET)
    public String get(Model model) {
        model.addAttribute("references", refRepo.findAll());
        return "list";
    }

    @RequestMapping(value = "/references/create/{type}", method = RequestMethod.GET)
    public String showForm(Model model, @PathVariable String type) {
        model.addAttribute("reference", new Reference(type));
        return "add_" + type;
    }

    @RequestMapping(value = "/save")
    public String addNew(Reference reference) {
        refRepo.save(reference);
        return "redirect:/references";
    }

    @RequestMapping(value = "/references", method = RequestMethod.POST)
    public String createReference(Model model, @RequestParam String type) {
        return "redirect:/references/create/" + type;
    }

    @RequestMapping(value = "/getbibtex", method = RequestMethod.GET)
    public String getBibtex(Model model) {
        //List<Reference> result = refRepo.findAll();
        String bibtex = "";
        for (Reference r : refRepo.findAll()) {
            bibtex += "@" + r.getType() + "{" + r.getName() + "\n";
            bibtex += r.getAuthor() != null ? " author    = \"" + r.getAuthor() + "\",\n" : "";
            bibtex += r.getTitle() != null ? " title     = \"" + r.getTitle() + "\",\n" : "";
            bibtex += r.getPublisher() != null ? " publisher = \"" + r.getPublisher() + "\",\n" : "";
            bibtex += r.getYear() != null ? " year      = \"" + r.getYear() + "\",\n" : "";
            bibtex += r.getMonth() != null ? " month     = \"" + r.getMonth() + "\",\n" : "";
            bibtex += r.getAddress() != null ? " address   = \"" + r.getAddress() + "\",\n" : "";
            bibtex += r.getEdition() != null ? " edition   = \"" + r.getEdition() + "\",\n" : "";
            bibtex += r.getJournal() != null ? " journal   = \"" + r.getJournal() + "\",\n" : "";
            bibtex += r.getVolume() != null ? " volume    = \"" + r.getVolume() + "\",\n" : "";
            bibtex += r.getNumber() != null ? " number    = \"" + r.getNumber() + "\",\n" : "";
            bibtex += r.getPages() != null ? " pages     = \"" + r.getPages() + "\",\n" : "";
            bibtex += r.getNote() != null ? " note       = \"" + r.getNote() + "\",\n" : "";
            bibtex += r.getKey() != null ? " key          = \"" + r.getKey() + "\",\n" : "";
            bibtex += r.getPublisher() != null ? " publisher = \"" + r.getPublisher() + "\",\n" : "";
            bibtex += r.getSeries() != null ? " series   = \"" + r.getSeries() + "\",\n" : "";
            bibtex = bibtex.substring(0, bibtex.length() - 2);
            bibtex += "\n}\n\n";
        }

        model.addAttribute("bibtexString", bibtex);
        return ("bibtex");
    }
}