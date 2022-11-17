package ar.edu.utn.frba.dds.interfaces.input;

import com.opencsv.bean.processor.StringProcessor;

public class ProcesarEspacios implements StringProcessor {

    private String defaultValue;

    @Override
    public String processString(String s) {
        if(s == null || s.trim().isEmpty())
            s = defaultValue;
        return s.trim();
    }

    @Override
    public void setParameterString(String s) {
        defaultValue = s;
    }
}
