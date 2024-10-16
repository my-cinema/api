package org.inin.mycinema.archiving;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public class ArchivingController {

    @RequestMapping("/addArchive")
    public String addArch() {
        return "";
    }

    @RequestMapping("/updateArchive")
    public String updateArch() {
        return "";
    }

    @RequestMapping("/deleteArchive")
    public String deleteArch() {
        return "";
    }

    @RequestMapping("/countArchive")
    public int countArch() {
        return 0;
    }

    @RequestMapping("/searchMovies")
    public String searchMovies() {
        return "";
    }
}
