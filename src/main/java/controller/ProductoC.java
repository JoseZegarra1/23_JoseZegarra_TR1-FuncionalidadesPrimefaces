package controller;

import dao.ProductoImpl;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import model.Producto;
import org.primefaces.PrimeFaces;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.component.export.PDFOptions;
import org.primefaces.component.export.PDFOrientationType;


@Named(value = "producto")
@SessionScoped
public class ProductoC implements Serializable {

    private ExcelOptions excelOpt;
    private PDFOptions pdfOpt;
    private ProductoImpl dao = new ProductoImpl();
    private Producto pro = new Producto();
    private List<Producto> listadopro;
    private String estado = "A";
    
public ProductoC(){
    
    customizationOptions();
}
    
    public void registrar() throws Exception {
        try {

            pro.setIDPROV(dao.obtenerCodigoProveedor(pro.getProv().getRAZSOCPROV()));

            int caso = 0;
            
            caso = dao.validar(pro, caso);
            
            switch (caso) {
                case 0:
                    dao.registrar(pro);
                    PrimeFaces.current().ajax().update("frmregistrar:dlgProducto");
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Registro exitoso"));
                    limpiar();
                    listar();
                    break;
                case 1:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "El producto ya existe"));
                    break;
            }
            
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en RegistrarC Producto: {0}", e.getMessage());

        }

    }

    public void modificar() throws Exception {
        try {
            pro.setIDPROV(dao.obtenerCodigoProveedor(pro.getProv().getRAZSOCPROV()));
            int caso = 0;
            caso = dao.validarProductoModificar(pro, caso);
            switch (caso) {
                case 0:
                    dao.modificar(pro);
                    PrimeFaces.current().ajax().update("dlgDatos");
                    limpiar();
                    listar();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Modificacion exitosa"));
                    break;
                case 1:
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "El producto ya existe"));
                    break;
            }

        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en ModificarC Producto: {0}", e.getMessage());
        }

    }

    public void eliminar(Producto pro) throws Exception {
        try {

            dao.eliminar(pro);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "OK", "Eliminaste Exitosamente"));

            limpiar();
            listar();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en eliminarC Producto: {0}", e.getMessage());
        }

    }

    public void restaurar(Producto pro) throws Exception {
        try {
            dao.restaurar(pro);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", "Restauraste este producto exitosamente"));
            limpiar();
            listar();
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en restaurarC Producto: {0}", e.getMessage());
        }

    }

    public void listar() {
        try {
            System.out.println("mi lastado es" + listadopro);
            listadopro = dao.listarD(estado);
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listar ProductoC {0}", e.getMessage());
        }

    }

    public List<String> completeTextProveedor(String query) throws Exception {
        try {
            return dao.autoCompleteProveedor(query);
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listar Proveedor/completeTextProveedor: {0}", e.getMessage());
            throw e;
        }
    }

    public List<String> completeTextProducto(String query) throws Exception {
        try {
            return dao.autoCompleteProducto(query);
        } catch (Exception e) {
            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en listar Proveedor/completeTextProveedor: {0}", e.getMessage());
            throw e;
        }
    }
    
    
    

    
    
    public void customizationOptions() {
        excelOpt = new ExcelOptions();
        excelOpt.setFacetBgColor("#19C7FF");
        excelOpt.setFacetFontSize("10");
        excelOpt.setFacetFontColor("#FFFFFF");
        excelOpt.setFacetFontStyle("BOLD");
        excelOpt.setCellFontColor("#000000");
        excelOpt.setCellFontSize("8");
        excelOpt.setFontName("Verdana");
        pdfOpt = new PDFOptions();
        pdfOpt.setFacetBgColor("#19C7FF");
        pdfOpt.setFacetFontColor("#000000");
        pdfOpt.setFacetFontStyle("BOLD");
        pdfOpt.setCellFontSize("12");
        pdfOpt.setFontName("Courier");
        pdfOpt.setOrientation(PDFOrientationType.LANDSCAPE);
    }


    public void limpiar() {
        pro = new Producto();

    }

    public ProductoImpl getDao() {
        return dao;
    }

    public void setDao(ProductoImpl dao) {
        this.dao = dao;
    }

    public Producto getPro() {
        return pro;
    }

    public void setPro(Producto pro) {
        this.pro = pro;
    }

    

    

    public ExcelOptions getExcelOpt() {
        return excelOpt;
    }

    public void setExcelOpt(ExcelOptions excelOpt) {
        this.excelOpt = excelOpt;
    }

    public PDFOptions getPdfOpt() {
        return pdfOpt;
    }

    public void setPdfOpt(PDFOptions pdfOpt) {
        this.pdfOpt = pdfOpt;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Producto> getListadopro() {
        return listadopro;
    }

    public void setListadopro(List<Producto> listadopro) {
        this.listadopro = listadopro;
    }
}
