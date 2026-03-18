package io.github.qualiscapes.controller;

import io.github.qualiscapes.exception.ExcelProcessingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExcelProcessingException.class)
    public String handleExcelError(
            ExcelProcessingException ex,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericError(
            Exception ex,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute(
                "errorMessage",
                "Ocorreu um erro inesperado ao processar a solicitação."
        );
        return "redirect:/";
    }
}
