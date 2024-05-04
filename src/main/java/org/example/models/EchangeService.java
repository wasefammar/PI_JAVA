package org.example.models;

import java.time.LocalDateTime;

public class EchangeService {

    private int id;
    private Service serviceIn;
    private Service serviceOut;
    private LocalDateTime dateEchange;
    private Boolean valide;


    public EchangeService(Service serviceIn, Service serviceOut, LocalDateTime dateEchange, Boolean valide) {
        this.serviceIn = serviceIn;
        this.serviceOut = serviceOut;
        this.dateEchange = dateEchange;
        this.valide = valide;
    }

    public int getId() {
        return id;
    }

    public Service getServiceIn() {
        return serviceIn;
    }

    public void setServiceIn(Service serviceIn) {
        this.serviceIn = serviceIn;
    }

    public Service getServiceOut() {
        return serviceOut;
    }

    public void setServiceOut(Service serviceOut) {
        this.serviceOut = serviceOut;
    }

    public LocalDateTime getDateEchange() {
        return dateEchange;
    }

    public void setDateEchange(LocalDateTime dateEchange) {
        this.dateEchange = dateEchange;
    }

    public Boolean isValide() {
        return valide;
    }

    public void setValide(Boolean valide) {
        this.valide = valide;
    }

    @Override
    public String toString() {
        return "EchangeService{" +
                "id=" + id +
                ", serviceIn=" + serviceIn +
                ", serviceOut=" + serviceOut +
                ", dateEchange=" + dateEchange +
                ", valide=" + valide +
                '}';
    }

    public String getServiceName() {
        return serviceIn.getTitreService();
    }
}