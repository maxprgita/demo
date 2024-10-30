package com.aips.mio.utility;

import org.springframework.beans.factory.annotation.Value;

import com.aips.mio.interfacciaFunz.ControlloInputNumero;
import com.aips.mio.interfacciaFunz.RitornaValNum;

public class OperazioniSuCampi {

    @Value("${utility.numeroNoSupportato}")
    private String numeroStrano;

    @Value("${utility.stringaNoNumero}")
    private String stringaNoNumero;

    // Metodo per controllare se una stringa contiene solo numeri
    public Boolean controlloStringaSeNum(String in1) {
        ControlloInputNumero stringIsNum = str -> str.matches("\\d+");
        return stringIsNum.controlloInputSeNumero(in1);
    }

    // Implementazione del metodo usando la lambda e l'interfaccia funzionale
    @SuppressWarnings("unchecked")
    public <T extends Number> T ritornaValoreTipoScelto(String in1, String sTipo) {
        // Implementazione della conversione usando l'interfaccia funzionale
        RitornaValNum<String, String, T> conversione = (stringa, tipoNum) -> {
            if (controlloStringaSeNum(stringa)) {
                switch (tipoNum) {
                    case "Integer":
                        return (T) Integer.valueOf(stringa);
                    case "Long":
                        return (T) Long.valueOf(stringa);
                    default:
                        throw new IllegalArgumentException(numeroStrano + " " + tipoNum);
                }
            } else {
                throw new NumberFormatException(stringaNoNumero + " " + stringa);
            }
        };

        // Uso dell'interfaccia funzionale per fare la conversione con i parametri in1 e sTipo
        return conversione.ritornaValoreTipoScelto(in1, sTipo);
    }
}