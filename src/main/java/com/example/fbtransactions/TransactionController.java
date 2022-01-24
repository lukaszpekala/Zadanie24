package com.example.fbtransactions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class TransactionController {
    private final TransactionDao dao;

    public TransactionController(TransactionDao dao) {
        this.dao = dao;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "index";
    }

    @PostMapping("/add")
    public String add(Transaction transaction) {
        dao.save(transaction);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(TransactionType type, Model model) {
        List<Transaction> transactions = dao.findByType(type);
        model.addAttribute("transactions", transactions);
        return "list";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam int id, Model model) {
        if (dao.findById(id).isPresent()) {
            Transaction transaction = dao.findById(id).get();
            model.addAttribute("transaction", transaction);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/edit")
    public String edit(Transaction transaction) {
        dao.update(transaction);
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteForm(@RequestParam int id, Model model) {
        if (dao.findById(id).isPresent()) {
            Transaction transaction = dao.findById(id).get();
            model.addAttribute("transaction", transaction);
            return "delete";
        }
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(Transaction transaction) {
        dao.delete(transaction.getId());
        return "redirect:/";
    }
}
