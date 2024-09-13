package com.aips.mio.utility;

import org.springframework.beans.factory.annotation.Value;

import com.aips.mio.interfacciaFunz.ControlloInputNumero;

public class OperazioniSuCampi {
	
	@Value("${utility.numeroNoSupportato}")
	String numeroStrano;
	
    @Value("${utility.stringaNoNumero}")
    private String stringaNoNumero;
	
	
    public Boolean controlloStringaSeNum(String in1) {
        ControlloInputNumero stringIsNum = str -> str.matches("\\d+");

        return stringIsNum.controlloInputSeNumero(in1);
    }
    
    
    @SuppressWarnings("unchecked")
	public <T extends Number> T ritornaValoreTipoScelto(String in1, String sTipo) {
        if (controlloStringaSeNum(in1)) {
            switch (sTipo) {
                case "Integer": {
                    return (T) Integer.valueOf(in1);
                }
                case "Long": {
                    return (T) Long.valueOf(in1);
                }
                default: {
                    throw new IllegalArgumentException(numeroStrano + " " + sTipo);
                }
            }
        } else {
            throw new NumberFormatException(stringaNoNumero + " " + in1);
        }
    }
}
