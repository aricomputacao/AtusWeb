/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atus.exceptions;

/**
 *
 * @author gilmario
 */
public class PecaFileException extends Exception {

    public PecaFileException() {
    }

    public PecaFileException(String message) {
        super("não Existe Arquivo para essa peça");
    }

}
