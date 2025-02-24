
import edu.upb.tresenraya.aula.Persona;

///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package edu.upb.tresenraya.aula;
//
///**
// *
// * @author nicol
// */
//public class PersonaBuilder {
//    private PersonaParaClaseDel6 persona;
//    public PersonaBuilder() {
//        persona = new PersonaParaClaseDel6();
//    }
//    public PersonaBuilder nombre(String nombre) {
//        persona.setNombre(nombre);
//        return this;
//    }
//    public PersonaBuilder apellido(String apellido) {
//        this.persona.setApellido(apellido);
//        return this;
//    }
//    public PersonaParaClaseDel6 build() {
//        return this.persona;
//}
///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package edu.upb.tresenraya.aula;
//
///**
// *
// * @author nicol
// */
public class PersonaBuilder {
    private Persona persona;
    public PersonaBuilder() {
        persona = new Persona ();
    }
    public PersonaBuilder nombre(String nombre) {
        persona.setNombre(nombre);
        return this;
    }
    public PersonaBuilder apellido(String apellido) {
        this.persona.setApellido(apellido);
        return this;
    }
    public Persona build() {
        return this.persona;
    }
}
