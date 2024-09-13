package com.aips.mio.interfacciaFunz;

@FunctionalInterface
public interface RitornaValNum <F, T, U> {
    U ritornaValoreTipoScelto(F stringa, T tipoNum);
}
