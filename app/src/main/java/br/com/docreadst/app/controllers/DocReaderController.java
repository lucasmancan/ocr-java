package br.com.docreadst.app.controllers;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static br.com.docreadst.app.utils.ProcessingImg.processImg;

@RestController
public class DocReaderController {

    @GetMapping("/gerar/texto")
    public String greeting() throws IOException, TesseractException {
        File f = new ClassPathResource("input.jpg").getFile();

        String returnStr = "";

        if(f.exists()) {
            Tesseract it = new Tesseract();

            it.setDatapath(new ClassPathResource("./tessdata").getPath());

            returnStr = it.doOCR(processImg(f));
        }

        return returnStr;
    }
}