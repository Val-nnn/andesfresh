package com.andesfresh.portal.excepcion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;
@ControllerAdvice   
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ProductoNoEncontradoException.class)   
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productoNoEncontrado(ProductoNoEncontradoException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "mensaje-error";
    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accesoDenegado() {
        return "acceso-denegado";
    }
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String paginaNoExiste(Model model) {
        model.addAttribute("mensaje", "La página solicitada no existe.");
        return "mensaje-error";
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String errorGeneral(Exception ex, Model model) {
        log.error("Error no controlado", ex);   
        model.addAttribute("mensaje", "Ocurrió un error interno. Intente nuevamente.");
        return "mensaje-error";
    }
}