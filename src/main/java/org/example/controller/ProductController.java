package org.example.controller;

import org.example.model.Music;
import org.example.model.MusicForm;
import org.example.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/music")
public class ProductController {
    @Autowired
    private MusicService productService;

    @Value("${file-upload}")
    private String fileUpload;


    @GetMapping("")
    public String index(Model model) {
        List<Music> music = productService.findAll();
        model.addAttribute("products", music);
        return "/index";
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("musicForm", new MusicForm());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveProduct(@ModelAttribute MusicForm musicForm) {
        MultipartFile multipartFile = musicForm.getSong();
        String fileName = multipartFile.getOriginalFilename();


        if (!productService.isValidFileExtension(fileName)) {
            musicForm.setErrorMessage("Invalid file type. Only .mp3, .wav, .ogg, .m4p files are allowed.");
            ModelAndView modelAndView = new ModelAndView("/create");
            modelAndView.addObject("musicForm", musicForm);
            return modelAndView;
        }

        try {
            FileCopyUtils.copy(musicForm.getSong().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Music music = new Music(musicForm.getId(), musicForm.getName(),
                musicForm.getDescription(), fileName);
        productService.save(music);

        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("musicForm", musicForm);
        modelAndView.addObject("message", "Thêm thành công !");
        return modelAndView;
    }


}
