package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.personas.Miembro;
import ar.edu.utn.frba.dds.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.trayectos.Tramo;
import ar.edu.utn.frba.dds.trayectos.Trayecto;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ParserTrayectos {

    public List<Trayecto> generarTrayectos(String archivo, List<Organizacion> organizaciones, List<MedioDeTransporte> medios) throws Exception, NoExisteMedioException {
        List<Trayecto> trayectos = new ArrayList<>();

        FileReader fileDescriptor = new FileReader(archivo);
        CSVReader csvReader = new CSVReader(fileDescriptor);
        String[] nextRecord = csvReader.readNext();

        int nroTramo = 0; // Para log de error
        while ((nextRecord = csvReader.readNext()) != null) {
            nroTramo++;
            String [] data = new String[9];
            int i = 0;
            for (String cell : nextRecord) {
                if(cell != null) {
                    data[i]=cell.trim();
                }
                i++;
            }

            Miembro miembro;
            try {
                miembro = organizaciones.stream()
                        .flatMap(organizacion -> organizacion.miembros().stream())
                        .filter(unMiembro -> unMiembro.getNroDocumento().equals(Integer.parseInt(data[1].trim())))
                        .findFirst().get();
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException("El miembro mencionado en el tramo numero " + nroTramo + " no existe");
            }

            Trayecto trayecto;
            if(data[0].equals("1")) {
                System.out.println("Cargo nuevo trayecto");
                trayecto = new Trayecto();
                miembro.agregarTrayecto(trayecto);
            } else {
                trayecto = miembro.getTrayecto(miembro.cantidadTrayectos());
            }

            System.out.println("Cargo nuevo tramo");
            MedioDeTransporte medioDeTransporte = medios.stream()
                    .filter( me -> me.toString().equals(data[6]) && me.matchAtributo1(data[7]) && me.matchAtributo2(data[8]))
                    .findFirst().orElseThrow(NoExisteMedioException::new);

            Coordenada coordenadaInicial = new Coordenada(Float.parseFloat(data[2]), Float.parseFloat(data[3]));
            Coordenada coordenadaFinal = new Coordenada(Float.parseFloat(data[4]), Float.parseFloat(data[5]));

            trayecto.agregarTramo(new Tramo(medioDeTransporte, coordenadaInicial, coordenadaFinal));
        }

        return trayectos;
    }
}
