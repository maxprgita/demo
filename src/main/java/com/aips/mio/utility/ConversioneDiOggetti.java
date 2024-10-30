package com.aips.mio.utility;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.aips.mio.dto.ProdottoResponseDTO;
import com.aips.mio.entity.Prodotto;

@Component
public class ConversioneDiOggetti {
	 //Converti la lista di prodotti in una lista di ProdottoResponseDTO
	public List<ProdottoResponseDTO> convertListProductIntoProdottoResponseDTO
						(List<Prodotto> prodotti){
		
        return prodotti.stream()
                .map(prodotto -> new ProdottoResponseDTO(
                    prodotto.getId(),
                    prodotto.getPrezzo(),
                    prodotto.getDescrizione(),
                    prodotto.getGenere()
                ))
                .collect(Collectors.toList());
	}
	
    public Locale handleLocale(Locale locale) {
    	return locale = locale == null ? Locale.ITALIAN : locale;
    }
}
