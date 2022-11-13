package ar.edu.utn.frba.dds.servicios.calculadoraDistancias;

import ar.edu.utn.frba.dds.entities.lugares.geografia.Direccion;
import ar.edu.utn.frba.dds.entities.lugares.geografia.UbicacionGeografica;
import ar.edu.utn.frba.dds.servicios.calculadoraDistancias.ddstpa.*;

import java.io.IOException;
import java.util.List;

public class AdaptadorServicioDDSTPA implements CalculadoraDistancias {
    //SERIA EL ROL ADAPTADOR DEL PATRON ADAPTER PARA EL SERVICIO DDSTPA

    private ServicioDDSTPA servicioExterno = ServicioDDSTPA.getInstancia();


    @Override
    public Float calcularDistancia(UbicacionGeografica ubicacionInicial, UbicacionGeografica ubicacionFinal) {

        Float valorDistancia = 0F;
        String unidadDistancia = "KM";

        try {
            int idLocalidadOrigen = obtenerIdLocalidad(ubicacionInicial.getDireccion());//1;
            String calleOrigen = ubicacionInicial.getDireccion().getCalle();//"maipu";
            int alturaOrigen = ubicacionInicial.getDireccion().getNumero();//100;
            int idLocalidadDestino = obtenerIdLocalidad(ubicacionFinal.getDireccion());//457;
            String calleDestino = ubicacionFinal.getDireccion().getCalle();//"O'Higgins";
            int alturaDestino = ubicacionFinal.getDireccion().getNumero();//200;

            DistanciaGson distancia = servicioExterno.distancia(idLocalidadOrigen,
                    calleOrigen,
                    alturaOrigen,
                    idLocalidadDestino,
                    calleDestino,
                    alturaDestino);
            valorDistancia = distancia.getValor();
            unidadDistancia = distancia.getUnidad();
        } catch (IOException e) {
            //System.out.println(e.getMessage());
            System.out.println("ERROR API");
            e.printStackTrace();
        }
        System.out.println("DistanciaConAPI: " + valorDistancia + " " + unidadDistancia);

        if(unidadDistancia != "KM"){ //TODO
            //valorDistancia = valorDistancia * multiplicadorRespectoKM(unidadDistancia);
        }

//        throw new RuntimeException("Servicio Morido");
        return valorDistancia;
    }

    public void imprimirPaises() {
        List<PaisGson> paises = servicioExterno.paises();
        for(PaisGson unPais: paises){
            System.out.println(unPais.getId() + "| " + unPais.getNombre());
        }
    }

    public void imprimirProvincias(int idPais) {
        List<ProvinciaGson> provincias = servicioExterno.provincias(idPais);
        System.out.println("Id del pais para buscar provincias: " + idPais);
        for(ProvinciaGson unaProv: provincias){
            System.out.println(unaProv.getId() + "| " + unaProv.getNombre());
        }
    }

    public void imprimirMunicipios(int idProvincia) {
        List<MunicipioGson> municipios = servicioExterno.municipios(idProvincia);
        System.out.println("Id de la provincia para buscar municipios: " + idProvincia);
        for(MunicipioGson unMunicipio: municipios){
            System.out.println(unMunicipio.getId() + "| " + unMunicipio.getNombre());
        }
    }

    public void imprimirLocalidades(int idMunicipio) {
        List<LocalidadGson> localidades = servicioExterno.localidades(idMunicipio);
        System.out.println(("Id del municipio para buscar localidades: " + idMunicipio));
        for(LocalidadGson unaLocalidad: localidades){
            System.out.println(unaLocalidad.getId() + "| " + unaLocalidad.getNombre());
        }
    }


    public List<PaisGson> obtenerPaises() {
        return servicioExterno.paises();
    }

    public List<ProvinciaGson> obtenerProvincias(int idPais) {
        return servicioExterno.provincias(idPais);
    }

    public List<MunicipioGson> obtenerMunicipios(int idProvincia) {
        return servicioExterno.municipios(idProvincia);
    }

    public List<LocalidadGson> obtenerLocalidades(int idMunicipio) {
        return servicioExterno.localidades(idMunicipio);
    }

    public int obtenerIdPais(Direccion direccion) {
        String nombrePais = direccion.getMunicipio().getProvincia().getNombrePais();
        PaisGson pais = obtenerPaises().stream().filter(p-> p.getNombre().equals(nombrePais.toUpperCase())).findFirst().get();
        System.out.println("PAIS ENCONTRADO: " + pais.getId() + "| " + pais.getNombre());
        return pais.getId();
        //return obtenerPaises().stream().filter(p->p.nombre.equals(nombrePais)).findFirst().map(p->p.id).orElse(9); //Defecto Argentina
    }

    public int obtenerIdProvincia(Direccion direccion) {
        String nombreProvincia = direccion.getMunicipio().getProvincia().getNombre();
        ProvinciaGson provincia = obtenerProvincias(obtenerIdPais(direccion)).stream()
                .filter(p -> p.getNombre().equals(nombreProvincia.toUpperCase()))
                .findFirst().orElseThrow(() -> new ApiDistanciasException("provincia", nombreProvincia));
        System.out.println("PROVINCIA ENCONTRADA: " + provincia.getId() + "| " + provincia.getNombre());
        return provincia.getId();
        //return obtenerProvincias(obtenerIdPais("ARGENTINA")).stream().filter(p->p.nombre.equals(nombreProvincia)).findFirst().map(p->p.id).orElse(168); //Defecto BsAs
    }

    public int obtenerIdMunicipio(Direccion direccion) {
        String nombreMunicipio = direccion.getMunicipio().getNombre();
        MunicipioGson municipio = obtenerMunicipios(obtenerIdProvincia(direccion)).stream()
                .filter(p -> p.getNombre().equals(nombreMunicipio.toUpperCase()))
                .findFirst().orElseThrow(() -> new ApiDistanciasException("municipio", nombreMunicipio));
        System.out.println("MUNICIPIO ENCONTRADO: " + municipio.getId() + "| " + municipio.getNombre());
        return municipio.getId();
        //return obtenerMunicipios(obtenerIdProvincia("BUENOS AIRES")).stream().filter(m->m.nombre.equals(nombreMunicipio)).findFirst().map(m->m.id).orElse(335); //Defecto Avellaneda
    }

    public int obtenerIdLocalidad(Direccion direccion) {
        String nombreLocalidad = direccion.getLocalidad();
        LocalidadGson localidad = obtenerLocalidades(obtenerIdMunicipio(direccion)).stream()
                .filter(l -> l.getNombre().equals(nombreLocalidad.toUpperCase()))
                .findFirst().orElseThrow(() -> new ApiDistanciasException("localidad", nombreLocalidad));;
        System.out.println("LOCALIDAD ENCONTRADA: " + localidad.getId() + "| " + localidad.getNombre());
        return localidad.getId();
        //return obtenerLocalidades(obtenerIdMunicipio("AVELLANEDA")).stream().filter(l->l.nombre.equals(nombreLocalidad)).findFirst().map(l->l.id).orElse(3319); //Defecto Dock Sud
    }








    public int obtenerIdPais2(String nombrePais) {
        PaisGson pais = obtenerPaises().stream().filter(p-> p.getNombre().equals(nombrePais.toUpperCase())).findFirst().get();
        System.out.println("PAIS ENCONTRADO: " + pais.getId() + "| " + pais.getNombre());
        return pais.getId();
        //return obtenerPaises().stream().filter(p->p.nombre.equals(nombrePais)).findFirst().map(p->p.id).orElse(9); //Defecto Argentina
    }

    public int obtenerIdProvincia2(String nombreProvincia) {
        ProvinciaGson provincia = obtenerProvincias(obtenerIdPais2("ARGENTINA")).stream().filter(p-> p.getNombre().equals(nombreProvincia.toUpperCase())).findFirst().get();
        System.out.println("PROVINCIA ENCONTRADA: " + provincia.getId() + "| " + provincia.getNombre());
        return provincia.getId();
        //return obtenerProvincias(obtenerIdPais("ARGENTINA")).stream().filter(p->p.nombre.equals(nombreProvincia)).findFirst().map(p->p.id).orElse(168); //Defecto BsAs
    }

    public int obtenerIdMunicipio2(String nombreMunicipio) {
        MunicipioGson municipio = obtenerMunicipios(obtenerIdProvincia2("BUENOS AIRES")).stream().filter(p-> p.getNombre().equals(nombreMunicipio.toUpperCase())).findFirst().get();
        System.out.println("MUNICIPIO ENCONTRADO: " + municipio.getId() + "| " + municipio.getNombre());
        return municipio.getId();
        //return obtenerMunicipios(obtenerIdProvincia("BUENOS AIRES")).stream().filter(m->m.nombre.equals(nombreMunicipio)).findFirst().map(m->m.id).orElse(335); //Defecto Avellaneda
    }

    public int obtenerIdLocalidad2(String nombreLocalidad) {
        LocalidadGson localidad = obtenerLocalidades(obtenerIdMunicipio2("AVELLANEDA")).stream().filter(l-> l.getNombre().equals(nombreLocalidad.toUpperCase())).findFirst().get();
        System.out.println("LOCALIDAD ENCONTRADA: " + localidad.getId() + "| " + localidad.getNombre());
        return localidad.getId();
        //return obtenerLocalidades(obtenerIdMunicipio("AVELLANEDA")).stream().filter(l->l.nombre.equals(nombreLocalidad)).findFirst().map(l->l.id).orElse(3319); //Defecto Dock Sud
    }
}
