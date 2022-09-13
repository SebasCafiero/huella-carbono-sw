package ar.edu.utn.frba.dds.mihuella.parsers;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Coordenada;
import ar.edu.utn.frba.dds.entities.lugares.Organizacion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.entities.mediciones.FechaException;
import ar.edu.utn.frba.dds.entities.mediciones.Periodo;
import ar.edu.utn.frba.dds.entities.personas.Miembro;
import ar.edu.utn.frba.dds.entities.transportes.MedioDeTransporte;
import ar.edu.utn.frba.dds.entities.transportes.MedioFactory;
import ar.edu.utn.frba.dds.entities.trayectos.Tramo;
import ar.edu.utn.frba.dds.entities.trayectos.Trayecto;
import ar.edu.utn.frba.dds.mapping.PeriodoMapper;
import ar.edu.utn.frba.dds.mihuella.dto.NuevoTrayectoDTO;
import ar.edu.utn.frba.dds.mihuella.dto.TramoCSVDTO;
import ar.edu.utn.frba.dds.mihuella.dto.TrayectoCompartidoDTO;
import ar.edu.utn.frba.dds.repositories.utils.Repositorio;
import ar.edu.utn.frba.dds.mapping.TramosMapper;
import ar.edu.utn.frba.dds.mihuella.dto.*;
import ar.edu.utn.frba.dds.repositories.factories.FactoryRepositorio;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ParserTrayectos {
    private final Repositorio<Miembro> repoMiembros;
    private final Repositorio<Trayecto> repoTrayectos;
    private final Repositorio<MedioDeTransporte> repoMedios;
    
    public ParserTrayectos() {
        this.repoMiembros = FactoryRepositorio.get(Miembro.class);
        this.repoTrayectos = FactoryRepositorio.get(Trayecto.class);
        this.repoMedios = FactoryRepositorio.get(MedioDeTransporte.class);
    }

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
                        .flatMap(organizacion -> organizacion.getMiembros().stream())
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
                    char periodicidad = data[10].charAt(0);
//                    LocalDate periodo;
                    Periodo periodo = PeriodoMapper.toEntity(periodicidad, data[11]);

                    /*if(periodicidad == 'M'){
                        String[] mesYanio = data[11].split("/");
                        periodo = LocalDate.parse(mesYanio[1]+"-"+mesYanio[0]+"-01");
                    }
                    else if(periodicidad == 'A'){
                        periodo = LocalDate.parse(data[11] + "-01-01");
                    }
                    else throw new FechaException("Periodicidad Erronea"); //TODO FALTARIA VALIDAR TMB QUE LA FECHA ESTE BIEN EN FORMATO*/

                    trayecto = new Trayecto(periodo);
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
                //TODO pero ver de hacerlo con CSV builder

                UbicacionGeografica ubicacionInicial = new UbicacionGeografica(coordenadaInicial);
                UbicacionGeografica ubicacionFinal = new UbicacionGeografica(coordenadaFinal);
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

    public static List<Trayecto> generarTrayectos(String archivo) throws FileNotFoundException {
        List<TramoCSVDTO2> tramosDTO = new CsvToBeanBuilder(new FileReader(archivo))
                .withOrderedResults(false)
                .withType(TramoCSVDTO2.class)
                .build()
                .parse();

        //TODO VER DE EVITAR EL TrayectoId=0 EN QUE FOR

        //TODO VER DE REALIZAR OPERACIONES EN EL MAPPER

        Map<Integer, List<TramoCSVDTO2>> tramosSegunIdTrayecto = new HashMap<>();
        for(TramoCSVDTO2 tramoDTO : tramosDTO) {
            List<TramoCSVDTO2> tramosDTODeUnTrayecto = tramosSegunIdTrayecto.get(tramoDTO.idTrayecto);
            if(tramosDTODeUnTrayecto == null) tramosDTODeUnTrayecto = new ArrayList<>(); //TODO EL GET DEL MAP DA NULL SI NO EXISTE
            tramosDTODeUnTrayecto.add(tramoDTO);
            tramosSegunIdTrayecto.put(tramoDTO.idTrayecto, tramosDTODeUnTrayecto);
        }

//        List<Trayecto> trayectos = new ArrayList<>();
        Map<Integer, Trayecto> trayectosSegunId = new HashMap<>();

        for(Map.Entry<Integer, List<TramoCSVDTO2>> entryTramosPorTrayecto : tramosSegunIdTrayecto.entrySet()) {
            if(entryTramosPorTrayecto.getKey()!=0) {
                List<TramoCSVDTO2> tramosDTODeUnTrayecto = entryTramosPorTrayecto.getValue();

                //Creo el trayecto con el miembro ppal, periodicidad y fecha (puedo usar los datos de cualquier tramo)
                Map.Entry<Integer, Trayecto> trayectoSegunId = TramosMapper.modelarTrayecto(tramosDTODeUnTrayecto);
                Trayecto unTrayecto = trayectoSegunId.getValue();

                //Creo cada unos de los tramos del trayecto y luego los agrego al trayecto creado
                List<Tramo> tramosDeUnTrayecto = tramosDTODeUnTrayecto
                        .stream()
                        .map(TramosMapper::toEntity)
                        .collect(Collectors.toList());
                unTrayecto.agregarTramos(tramosDeUnTrayecto);

                trayectosSegunId.put(trayectoSegunId.getKey(),unTrayecto);
            }
        }

        //Se podria hacer al mismo tiempo en el for de las entries
        for(Trayecto unTrayecto : trayectosSegunId.values()) {
            for(Miembro unMiembro : unTrayecto.getMiembros()) { //deberia ser un unico miembro hasta el momento
                unMiembro.agregarTrayecto(unTrayecto);
            }
        }

        for(TramoCSVDTO2 tramoCompartidoDTO : tramosSegunIdTrayecto.get(0)) {
            TramosMapper.mapTrayectoCompartido(trayectosSegunId,tramoCompartidoDTO);
        }

        return new ArrayList<>(trayectosSegunId.values());
//        return trayectosSegunId.values().stream().collect(Collectors.toList());
    }
}
