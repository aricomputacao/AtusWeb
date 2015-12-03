/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
ajustaNumero = (function(e) {
    while (e.value.length < 7) {
        e.value = '0' + e.value;
    }
});

soNumero = (function(e, c) {
    var tecla = (window.event) ? event.keyCode : e.which;
    if (tecla !== 0) {
        if (tecla === 8 || tecla === 13) {
            return true;
        } else {
            var retorno = new String(String.fromCharCode(tecla));
            return retorno.replace(/[^0-9]/, '').length !== 0;
        }
    } else {
        return true;
    }

});

zero = (function(elemento) {
    var str = elemento.value;
    if (str.length <= 10) {
        while (str.length < 14)
            str = '0' + str;
    }
    if (str.length > 14) {
        str = str.substring(1);
    }
    elemento.value = str;
});

zeroNumero = (function(elemento, max) {
    var str = elemento.value;
    if (str.length <= max) {
        while (str.length < max)
            str = '0' + str;
    }
    if (str.length > max) {
        str = str.substring(1);
    }
    elemento.value = str;
});