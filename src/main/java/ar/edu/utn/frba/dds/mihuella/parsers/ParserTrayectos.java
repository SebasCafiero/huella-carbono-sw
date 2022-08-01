package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.lugares.Coordenada;
import ar.edu.utn.frba.dds.lugares.Organizacion;
import ar.edu.utn.frba.dds.lugares.UbicacionGeografica;
import ar.edu.utn.frba.dds.personas.Miembro;
import ar.edu.utn.frba.dds.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.transportes.MedioFactory;
import ar.edu.utn.frba.dds.trayectos.Tramo;
import ar.edu.utn.frba.dds.trayectos.Trayecto;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.*;

public class ParserTrayectos {

    public List<Trayecto> generarTrayectos(String archivo, List<Organizacion> organizaciones, List<MedioDeTransporte> medios) throws Exception, NoExisteMedioException {
        List<Trayecto> trayectos = new ArrayList<>();

        FileReader fileDescriptor = new FileReader(archivo);
        CSVReader csvReader = new CSVReader(fileDescriptor);
        String[] nextRecord = csvReader.readNext();
        int tamanioMaximo = nextRecord.length;

        int nroTramo = 0; // Para log de error
        while ((nextRecord = csvReader.readNext()) != null) {
            nroTramo++;
            String [] data = new String[tamanioMaximo];
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

            if(!data[0].equals("0")) {
                System.out.println("No es trayecto compartido pasivo");

                Optional<Trayecto> miTrayecto = trayectos.stream()
                        .filter(tr -> tr.getId().equals(Integer.parseInt(data[0].trim())))
                        .findFirst();

                if(miTrayecto.isPresent()) {
                    trayecto = miTrayecto.get();
                } else {
                    trayecto = new Trayecto();
                    trayecto.setId(Integer.parseInt(data[0].trim()));
                    trayectos.add(trayecto);
                }

                System.out.println("Cargo nuevo tramo");
                MedioDeTransporte medioSolicitado = new MedioFactory().getMedioDeTransporte(data[7], data[8], data[9]);
                Optional<MedioDeTransporte> medioDeTransporte = medios.stream()
                        .filter( me -> me.equals(medioSolicitado)).findFirst();

                if(!medioDeTransporte.isPresent()) {
                    throw new NoExisteMedioException(data[7], data[8], data[9]);
                }

                Coordenada coordenadaInicial = new Coordenada(Float.parseFloat(data[3]), Float.parseFloat(data[4]));
                Coordenada coordenadaFinal = new Coordenada(Float.parseFloat(data[5]), Float.parseFloat(data[6]));

                String lugar = "BsAs"; //TODO
                UbicacionGeografica ubicacionInicial = new UbicacionGeografica(lugar, coordenadaInicial);
                UbicacionGeografica ubicacionFinal = new UbicacionGeografica(lugar, coordenadaFinal);
                trayecto.agregarTramo(new Tramo(medioDeTransporte.get(), ubicacionInicial, ubicacionFinal));

            } else {
                // Si da error el get es porque se intentó referenciar con un trayecto
                // compartido a un lider de trayecto que no existe
                trayecto = trayectos.stream()
                        .filter(tr -> tr.getId().equals(Integer.parseInt(data[2].trim())))
                        .findFirst().get();
            }

            miembro.agregarTrayecto(trayecto);
            trayecto.agregarmiembro(miembro);
        }

        return trayectos;
    }
}
