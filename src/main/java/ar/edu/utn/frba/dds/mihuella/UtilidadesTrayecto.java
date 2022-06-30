package ar.edu.utn.frba.dds.mihuella;

import ar.edu.utn.frba.dds.lugares.*;
import ar.edu.utn.frba.dds.personas.Miembro;
import ar.edu.utn.frba.dds.personas.TipoDeDocumento;
import ar.edu.utn.frba.dds.transportes.*;
import ar.edu.utn.frba.dds.trayectos.Tramo;
import ar.edu.utn.frba.dds.trayectos.Trayecto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class UtilidadesTrayecto {
    public RepoOrganizaciones repoOrganizaciones;
    public RepoTransportes repoTransportes;
    public Map<String, Function<String[], MedioDeTransporte>> medioFactory;

    public UtilidadesTrayecto() {
        repoOrganizaciones = new RepoOrganizaciones();
        repoTransportes = new RepoTransportes();
        medioFactory = new HashMap<String, Function<String[], MedioDeTransporte>>();
        medioFactory.put("particular", VehiculoParticular::new);
//        medioFactory.put("publico", TransportePublico::new);
    }

    public Optional<MedioDeTransporte> getMedioDeTransporte(String nombreMedio, String atrb1, String atrb2) {
        return repoTransportes.findMedio(nombreMedio.toLowerCase(), atrb1.toUpperCase(), atrb2);
    }

    public void cargarOrganizaciones(String stringJSON) throws Exception {
        System.out.println("Carga de Organizaciones:\n" + stringJSON);

        JSONArray arrayOrg = new JSONArray(stringJSON);
        for (int orgIndex = 0; orgIndex < arrayOrg.length(); orgIndex++) {
            JSONObject org = arrayOrg.getJSONObject(orgIndex);

            String razonSocial = org.getString("organizacion");
            ClasificacionOrganizacion clasificacion = new ClasificacionOrganizacion(org.getString("clasificacion"));

            if(!Organizacion.existeTipoOrganizacion(org.getString("tipo")))
                throw new Exception("Error en el tipo de la organizacion. No existe el tipo " + org.getString("tipo"));

            TipoDeOrganizacionEnum tipoDeOrganizacion = TipoDeOrganizacionEnum.valueOf(org.getString("tipo"));
            UbicacionGeografica ubicacionOrg = new UbicacionGeografica(org.getString("ubicacion"), org.getFloat("latitud"), org.getFloat("longitud"));

            Organizacion nuevaOrg = new Organizacion(razonSocial, tipoDeOrganizacion, clasificacion, ubicacionOrg);
            repoOrganizaciones.agregarOrganizacion(nuevaOrg);

            JSONArray arraySectores = org.getJSONArray("sectores");
            for (int sectorIndex = 0; sectorIndex < arraySectores.length(); sectorIndex++) {
                JSONObject sector = arraySectores.getJSONObject(sectorIndex);

                String nombreSector = sector.getString("nombre");
                Sector nuevoSector = new Sector(nombreSector, nuevaOrg);

                JSONArray arrayMiembros = sector.getJSONArray("miembros");
                for (int miembroIndex = 0; miembroIndex < arrayMiembros.length(); miembroIndex++) {
                    JSONObject miembro = arrayMiembros.getJSONObject(miembroIndex);

                    String nombreMiembro = miembro.getString("nombre");
                    String apellido = miembro.getString("apellido");
                    TipoDeDocumento tipoDeDocumento = TipoDeDocumento.valueOf(miembro.getString("tipoDocumento"));
                    int documento = miembro.getInt("documento");

                    UbicacionGeografica ubicacionMiembro = new UbicacionGeografica(org.getString("ubicacion"), org.getFloat("latitud"), org.getFloat("longitud"));
                    Miembro nuevoMiembro = new Miembro(nombreMiembro, apellido, tipoDeDocumento, documento, ubicacionMiembro);

                    nuevoSector.agregarMiembro(nuevoMiembro);
                    repoOrganizaciones.agregarMiembro(nuevoMiembro);
                }
            }
        }
    }

    public void cargarTransportes(String stringJSON) throws Exception {
        System.out.println("Carga de Transportes:\n" + stringJSON);

        JSONArray arrayTransporte = new JSONArray(stringJSON);
        for (int orgIndex = 0; orgIndex < arrayTransporte.length(); orgIndex++) {
            JSONObject transporte = arrayTransporte.getJSONObject(orgIndex);
            MedioDeTransporte medioDeTransporte;
            String tipoMedio = transporte.getString("tipo");
            String subtipoMedio = transporte.getString("subtipo");

            if(tipoMedio.equals("contratado")) {
                medioDeTransporte = new ServicioContratado(new TipoServicio(subtipoMedio));
            } else if(tipoMedio.equals("publico")) {
                medioDeTransporte = new TransportePublico(
                        TipoTransportePublico.valueOf(subtipoMedio),
                        transporte.getString("linea")
                );
            } else if(tipoMedio.equals("ecologico")) {
                medioDeTransporte = new TransporteEcologico(
                        TipoTransporteEcologico.valueOf(subtipoMedio));
            } else /*if(tipoMedio.equals("particular"))*/ {
                medioDeTransporte = new VehiculoParticular(
                        TipoVehiculo.valueOf(subtipoMedio),
                        TipoCombustible.valueOf(transporte.getString("combustible"))
                        );
            }

            if(tipoMedio.equals("publico")) {
                JSONArray arrayParadas = transporte.getJSONArray("paradas");
                for (int paradaIndex = 0; paradaIndex < arrayParadas.length(); paradaIndex++) {
                    JSONObject parada = arrayParadas.getJSONObject(paradaIndex);

                    ((TransportePublico) medioDeTransporte).agregarParada(new Parada(
                            new Coordenada(parada.getFloat("latitud"), parada.getFloat("longitud")),
                            parada.getFloat("distanciaAnterior"),
                            parada.getFloat("distanciaAnterior"))
                    );

                }
            }
            repoTransportes.agregarMedio(medioDeTransporte);
        }
    }

    public void mostrarResultadosOrganizaciones() {
        System.out.println( "Las organizaciones son: ");
        for(Organizacion unaOrg : repoOrganizaciones.getOrganizaciones()){
            System.out.print( "Nombre: " + unaOrg.getRazonSocial());
            System.out.print( ", ubicacion: " + unaOrg.getUbicacion().getLocalidad());
            System.out.print( ", clasificacion: " + unaOrg.getClasificacionOrganizacion().getNombre());
            System.out.println();
            System.out.println( "Los sectores son: ");
            for(Sector unSector : unaOrg.getSectores()) {
                System.out.print("\tNombre: " + unSector.getNombre());
                System.out.println();
                System.out.println( "\tLos miembros son: ");
                for (Miembro unMiembro : unSector.getListaDeMiembros()) {
                    System.out.print("\t\tNombre: " + unMiembro.getNombre());
                    System.out.print(", apellido: " + unMiembro.getApellido());
                    System.out.print(", documento: " + unMiembro.getNroDocumento());
                    for(Trayecto trayecto : unMiembro.getTrayectos()) {
                        System.out.println( "\t\t\tTrayecto: ");
                        for(Tramo tramo : trayecto.getTramos()) {
                            System.out.println( "\t\t\t\tTramo: ");
                            System.out.println("\t\t\t\t\tCoor inicial: " +
                                    tramo.getCoordenadaInicial().getLatitud().toString() + "-" +
                                    tramo.getCoordenadaInicial().getLongitud().toString()
                            );
                            System.out.println("\t\t\t\t\tCoor final: " +
                                    tramo.getCoordenadaFinal().getLatitud().toString() + "-" +
                                    tramo.getCoordenadaFinal().getLongitud().toString()
                            );
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println();
        }
    }

    public void mostrarResultadosTransportes() {
        System.out.println( "Los transportes son: ");
        for(MedioDeTransporte medioDeTransporte : repoTransportes.getMedios()){
            System.out.println( "Nombre: " + medioDeTransporte.toString());
            if(medioDeTransporte.toString().equals("publico")) {
                for (Parada parada : ((TransportePublico) medioDeTransporte).getParadas() ) {
                    System.out.print("\t\tLatitud: " + parada.getCoordenada().getLatitud().toString());
                    System.out.print(", longitud: " + parada.getCoordenada().getLongitud().toString());
                    System.out.println();
                }
            }
            System.out.println();
        }
    }

    public void cargarTrayecto(String[] parametros) throws Exception {
        String [] data = new String[9];
        int i = 0;
        for (String cell : parametros) {
            if(cell != null) {
                data[i]=cell.trim();
            }
            i++;
        }

        Miembro miembro = repoOrganizaciones.findMiembro(Integer.parseInt(data[1].trim())).
                orElseThrow(Exception::new);

        Trayecto trayecto;
        if(data[0].equals("1")) {
            System.out.println("Cargo nuevo trayecto");
            trayecto = new Trayecto();
            miembro.agregarTrayecto(trayecto);
        } else {
            trayecto = miembro.getTrayecto(miembro.cantidadTrayectos());
        }

        System.out.println("Cargo nuevo tramo");
        MedioDeTransporte medioDeTransporte = getMedioDeTransporte(data[6], data[7], data[8]).
                orElseThrow(Exception::new);

        Coordenada coordenadaInicial = new Coordenada(Float.parseFloat(data[2]), Float.parseFloat(data[3]));
        Coordenada coordenadaFinal = new Coordenada(Float.parseFloat(data[4]), Float.parseFloat(data[5]));

        trayecto.agregarTramo(new Tramo(medioDeTransporte, coordenadaInicial, coordenadaFinal));
    }

}
