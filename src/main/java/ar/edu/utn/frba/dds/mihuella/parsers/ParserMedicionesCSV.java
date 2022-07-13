package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.excepciones.FechaException;
import ar.edu.utn.frba.dds.mediciones.Categoria;
import ar.edu.utn.frba.dds.mediciones.Medicion;
import ar.edu.utn.frba.dds.mihuella.fachada.Medible;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ParserMedicionesCSV implements ParserMediciones {
    @Override
    public List<Medible> generarMediciones(String archivo) throws FechaException, IOException {
        List<Medible> mediciones = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader(archivo));
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                String [] data = new String[6];
                int i = 0;
                for (String cell : nextRecord) {
                    data[i]=cell;
                    i++;
                }
                Categoria cat = new Categoria(data[0],data[1]);

                char periodicidad = data[4].charAt(0);
                LocalDate periodo;

                if(periodicidad == 'M'){
                    String[] mesYanio = data[5].split("/");
                    periodo = LocalDate.parse(mesYanio[1]+"-"+mesYanio[0]+"-01");
                }
                else if(periodicidad == 'A'){
                    periodo = LocalDate.parse(data[5] + "-01-01");
                }
                else throw new FechaException("Periodicidad Erronea"); //TODO FALTARIA VALIDAR TMB QUE LA FECHA ESTE BIEN EN FORMATO

                mediciones.add(new Medicion(cat, data[2], Float.parseFloat(data[3]), periodicidad, periodo));
            }
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("El archivo " + archivo + " no existe");
        } catch (FechaException e) {
            throw new FechaException("El formato de la fecha es incorrecto");
        } catch (IOException e) {
            throw new IOException();
        }

        return mediciones;
    }
}

/*
    String extensionArchivo = "";
    int index = archivo.lastIndexOf('.');
    if (index > 0)
        extensionArchivo = archivo.substring(index + 1);
    else throw new ArchivoSinExtensionException(archivo);

    FileReader fileDescriptor;
    try {
        fileDescriptor = new FileReader(archivo);
    } catch (Exception e) {
        throw new ArchivoInexistenteException(archivo);
    }

    if (extensionArchivo.equals("csv")) {
        System.out.println("El archivo de mediciones es de tipo csv");
        return generarMedicionesCSV(fileDescriptor);
    } else if (extensionArchivo.equals("otraExtenion")) {
        return null;
    } else throw new TipoArchivoInvalidoException("There is not implemented a parser for reading " + extensionArchivo + " files");
*/