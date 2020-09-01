package com.test.changerates.controller;

import com.test.changerates.service.impl.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CurrenciesController {
    private Parser parser;

    @Autowired
    public void setParser(Parser parser) {
        this.parser = parser;
    }

    @GetMapping("/currencies")
    public String currencies(Model model) {
        parser.getCurrencies();
        model.addAttribute("usdgbp", parser.getUsdgbp());
        model.addAttribute("usdeur", parser.getUsdeur());
        model.addAttribute("usdcan", parser.getUsdcad());
        model.addAttribute("usdpln", parser.getUsdpln());
        return "currencies";
    }

    @PostMapping("/currencies")
    public String currenciesByDate(@ModelAttribute(name = "date") String date, Model model) {
        parser.getCurrencies(date);
        model.addAttribute("usdgbp", parser.getUsdgbp());
        model.addAttribute("usdeur", parser.getUsdeur());
        model.addAttribute("usdcan", parser.getUsdcad());
        model.addAttribute("usdpln", parser.getUsdpln());
        return "currencies";
    }
}
