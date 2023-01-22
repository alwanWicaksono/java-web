package testprogrammingnds.javaweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import testprogrammingnds.javaweb.model.Karyawan;
import testprogrammingnds.javaweb.repository.KaryawanRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Controller
public class KaryawanController {
    @Autowired
    private KaryawanRepository karyawanRepository;
    @GetMapping("/")
    public String getAllKaryawan(Model model, @Param("name") String name, @Param("entryDate1") String entryDate1, @Param("entryDate2") String entryDate2, @Param("noHp") String noHp) {
        try {
            if(entryDate1 != null && entryDate2 != null){
                if((entryDate1.length() == 0 && entryDate2.length() > 0 ) || (entryDate1.length() > 0 && entryDate2.length() == 0)){
                    model.addAttribute("message", "Date range \"Entry Date\" Harus Diisi keduanya jika ingin mencari range date");
                }
            }
            List<Karyawan> karyawan = new ArrayList<Karyawan>();
            karyawanRepository.findAll(name, entryDate1, entryDate2, noHp).forEach(karyawan::add);

            model.addAttribute("karyawan", karyawan);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "karyawan";
    }

    @GetMapping("/new")
    public String addKaryawan(Model model) {
        Karyawan karyawan = new Karyawan();
        model.addAttribute("karyawan", karyawan);
        model.addAttribute("pageTitle", "Create new Karyawan");

        return "karyawan-form";
    }

    @GetMapping("/{id}")
    public String editKaryawan(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Karyawan karyawan = karyawanRepository.findById(id);
            model.addAttribute("karyawan", karyawan);
            model.addAttribute("pageTitle", "Edit Karyawan");

            return "karyawan-form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/";
        }
    }

    @PostMapping("/save")
    public String saveKaryawan(Karyawan karyawan, RedirectAttributes redirectAttributes) {
        try {
            if(karyawan.getName().length() == 0 || karyawan.getNoHp().length() == 0 || karyawan.getEntryDate() == null){
                redirectAttributes.addFlashAttribute("message", "Data harus diisi");
                if(karyawan.getId() != 0){
                    return "redirect:/"  + karyawan.getId();
                }else{
                    return "redirect:/new";
                }
            }
            LocalDate dateBeforeTwoMonth = LocalDate.now().minusMonths(2);
            LocalDate dateAfterTreeMonth = LocalDate.now().plusMonths(3);
            LocalDate entryDate = karyawan.getEntryDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if(karyawan.getId() != 0){
                if(entryDate.isBefore(dateBeforeTwoMonth)){
                    redirectAttributes.addFlashAttribute("message", "Tanggal Masuk kerja tidak boleh 2 bulan lebih lama dari tanggal saat ini");
                    return "redirect:/" + karyawan.getId();
                }else if(entryDate.isAfter(dateAfterTreeMonth)){
                    redirectAttributes.addFlashAttribute("message", "Tanggal Masuk kerja tidak boleh 3 bulan lebih besar dari tanggal saat ini");
                    return "redirect:/" + karyawan.getId();
                }
                karyawanRepository.update(karyawan);
            }else{
                if(entryDate.isBefore(dateBeforeTwoMonth)){
                    redirectAttributes.addFlashAttribute("message", " Tanggal Masuk kerja tidak boleh 2 bulan lebih lama dari tanggal saat ini");
                    return "redirect:/new";
                }else if(entryDate.isAfter(dateAfterTreeMonth)){
                    redirectAttributes.addFlashAttribute("message", " Tanggal Masuk kerja tidak boleh 3 bulan lebih besar dari tanggal saat ini");
                    return "redirect:/new";
                }
                karyawanRepository.save(karyawan);
            }
            redirectAttributes.addFlashAttribute("message", "The Karyawan has been saved successfully!");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Nama tersebut telah terpakai");
            if(karyawan.getId() != 0){
                return "redirect:/"  + karyawan.getId();
            }else{
                return "redirect:/new";
            }
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteKaryawan(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            karyawanRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "The Karyawan with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/";
    }
}
