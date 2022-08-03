package ar.edu.utn.frba.dds.trayectos.servicios;

import ar.edu.utn.frba.dds.lugares.geografia.UbicacionGeografica2;
import ar.edu.utn.frba.dds.trayectos.servicios.ddstpa.*;

import java.io.IOException;
import java.util.List;

public class AdaptadorServicioDDSTPA {//implements CalculadoraDistancias{
    //SERIA EL ROL ADAPTADOR DEL PATRON ADAPTER PARA EL SERVICIO DDSTPA

    private ServicioDDSTPA servicioExterno = ServicioDDSTPA.getInstancia();


//    @Override
    public Float calcularDistancia(UbicacionGeografica2 ubicacionInicial, UbicacionGeografica2 ubicacionFinal) {

        Float valorDistancia = 0F;
        String unidadDistancia = "KM";

        try {
            int idLocalidadOrigen = obtenerIdLocalidad(ubicacionInicial.getDireccion().getLocalidad());//1;
            String calleOrigen = ubicacionInicial.getDireccion().getCalle();//"maipu";
            int alturaOrigen = ubicacionInicial.getDireccion().getNumero();//100;
            int idLocalidadDestino = obtenerIdLocalidad(ubicacionFinal.getDireccion().getLocalidad());//457;
            String calleDestino = ubicacionFinal.getDireccion().getCalle();//"O'Higgins";
            int alturaDestino = ubicacionFinal.getDireccion().getNumero();//200;

            Distancia distancia = servicioExterno.distancia(idLocalidadOrigen,
                    calleOrigen,
                    alturaOrigen,
                    idLocalidadDestino,
                    calleDestino,
                    alturaDestino);
            valorDistancia = distancia.valor;
            unidadDistancia = distancia.unidad;
        } catch (IOException e) {
            //System.out.println(e.getMessage());
            System.out.println("ERROR API");
            e.printStackTrace();
        }
        System.out.println("DistanciaConAPI: " + valorDistancia + " " + unidadDistancia);

        if(unidadDistancia != "KM"){
            //valorDistancia = valorDistancia * multiplicadorRespectoKM(unidadDistancia);
        }
        return valorDistancia;
    }

    public void imprimirPaises() throws IOException {
        List<PaisGson> paises = servicioExterno.paises();
        for(PaisGson unPais: paises){
            System.out.println(unPais.id + "| " + unPais.nombre);
        }
    }

    public void imprimirProvincias(int idPais) throws IOException {
        List<ProvinciaGson> provincias = servicioExterno.provincias(idPais);
        System.out.println("Id del pais para buscar provincias: " + idPais);
        for(ProvinciaGson unaProv: provincias){
            System.out.println(unaProv.id + "| " + unaProv.nombre);
        }
    }

    public void imprimirMunicipios(int idProvincia) throws IOException {
        List<MunicipioGson> municipios = servicioExterno.municipios(idProvincia);
        System.out.println("Id de la provincia para buscar municipios: " + idProvincia);
        for(MunicipioGson unMunicipio: municipios){
            System.out.println(unMunicipio.id + "| " + unMunicipio.nombre);
        }
    }

    public void imprimirLocalidades(int idMunicipio) throws IOException {
        List<LocalidadGson> localidades = servicioExterno.localidades(idMunicipio);
        System.out.println(("Id del municipio para buscar localidades: " + idMunicipio));
        for(LocalidadGson unaLocalidad: localidades){
            System.out.println(unaLocalidad.id + "| " + unaLocalidad.nombre);
        }
    }


    public List<PaisGson> obtenerPaises() throws IOException {
        return servicioExterno.paises();
    }

    public List<ProvinciaGson> obtenerProvincias(int idPais) throws IOException {
        return servicioExterno.provincias(idPais);
    }

    public List<MunicipioGson> obtenerMunicipios(int idProvincia) throws IOException {
        return servicioExterno.municipios(idProvincia);
    }

    public List<LocalidadGson> obtenerLocalidades(int idMunicipio) throws IOException {
        return servicioExterno.localidades(idMunicipio);
    }

    public int obtenerIdPais(String nombrePais) throws IOException {
        PaisGson pais = obtenerPaises().stream().filter(p->p.nombre.equals(nombrePais.toUpperCase())).findFirst().get();
        System.out.println("PAIS ENCONTRADO: " + pais.id + "| " + pais.nombre);
        return pais.id;
        //return obtenerPaises().stream().filter(p->p.nombre.equals(nombrePais)).findFirst().map(p->p.id).orElse(9); //Defecto Argentina
    }

    public int obtenerIdProvincia(String nombreProvincia) throws IOException {
        ProvinciaGson provincia = obtenerProvincias(obtenerIdPais("ARGENTINA")).stream().filter(p->p.nombre.equals(nombreProvincia.toUpperCase())).findFirst().get();
        System.out.println("PROVINCIA ENCONTRADA: " + provincia.id + "| " + provincia.nombre);
        return provincia.id;
        //return obtenerProvincias(obtenerIdPais("ARGENTINA")).stream().filter(p->p.nombre.equals(nombreProvincia)).findFirst().map(p->p.id).orElse(168); //Defecto BsAs
    }

    public int obtenerIdMunicipio(String nombreMunicipio) throws IOException {
        MunicipioGson municipio = obtenerMunicipios(obtenerIdProvincia("BUENOS AIRES")).stream().filter(p->p.nombre.equals(nombreMunicipio.toUpperCase())).findFirst().get();
        System.out.println("MUNICIPIO ENCONTRADO: " + municipio.id + "| " + municipio.nombre);
        return municipio.id;
        //return obtenerMunicipios(obtenerIdProvincia("BUENOS AIRES")).stream().filter(m->m.nombre.equals(nombreMunicipio)).findFirst().map(m->m.id).orElse(335); //Defecto Avellaneda
    }

    public int obtenerIdLocalidad(String nombreLocalidad) throws IOException {
        LocalidadGson localidad = obtenerLocalidades(obtenerIdMunicipio("AVELLANEDA")).stream().filter(l->l.nombre.equals(nombreLocalidad.toUpperCase())).findFirst().get();
        System.out.println("LOCALIDAD ENCONTRADA: " + localidad.id + "| " + localidad.nombre);
        return localidad.id;
        //return obtenerLocalidades(obtenerIdMunicipio("AVELLANEDA")).stream().filter(l->l.nombre.equals(nombreLocalidad)).findFirst().map(l->l.id).orElse(3319); //Defecto Dock Sud
    }
}
