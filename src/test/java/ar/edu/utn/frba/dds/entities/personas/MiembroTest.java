package ar.edu.utn.frba.dds.entities.personas;

public class MiembroTest {

//TODO VER DE USAR @BEFORE O AGRUPAR DECLARACIONES REPETIDAS

//    @Test
//    public void vinculoMiembroConOrganizacion() throws SectorException, MiembroException {
//
//        Organizacion unaOrg = new Organizacion("miRazonSocial",
//                TipoDeOrganizacionEnum.EMPRESA,
//                new ClasificacionOrganizacion("Sector Primario"),
//                new UbicacionGeografica(new Coordenada(10F,5F)));
//
//        Sector unSector = new Sector("miSector",unaOrg);
//
//        Miembro miembro1 = new Miembro("jose", "pepito", TipoDeDocumento.DNI,12345);
//
//        miembro1.solicitarIngresoAlSector(unSector);
//
//        Assertions.assertEquals(1,miembro1.cantidadDeSectoresDondeTrabaja());
//        Assertions.assertEquals(1,miembro1.cantidadDeOrganizacionesDondeTrabaja());
//        Assertions.assertEquals(true,unSector.esMiembro(miembro1));
//        Assertions.assertEquals(true,miembro1.trabajaEnSector(unSector));
//    }

//    @Test
//    public void vinculoMiembroConOrganizacionYLoQuito() throws SectorException, MiembroException {
//
//        Organizacion unaOrg = new Organizacion("miRazonSocial",
//                TipoDeOrganizacionEnum.EMPRESA,
//                new ClasificacionOrganizacion("Sector secundario"),
//                new UbicacionGeografica(new Coordenada(10F,5F)));
//
//        Sector unSector = new Sector("miSector",unaOrg);
//
//        Miembro unMiembro = new Miembro("jose", "pepito",TipoDeDocumento.DNI,12345);
//
//        unMiembro.solicitarIngresoAlSector(unSector);
//
//        Assertions.assertEquals(1,unMiembro.cantidadDeSectoresDondeTrabaja());
//        Assertions.assertEquals(1,unMiembro.cantidadDeOrganizacionesDondeTrabaja());
//        Assertions.assertEquals(true,unSector.esMiembro(unMiembro));
//        Assertions.assertEquals(true,unMiembro.trabajaEnSector(unSector));
//
//        unSector.quitarMiembro(unMiembro);
//
//        Assertions.assertEquals(0,unMiembro.cantidadDeSectoresDondeTrabaja());
//        Assertions.assertEquals(0,unMiembro.cantidadDeOrganizacionesDondeTrabaja());
//        Assertions.assertEquals(false,unSector.esMiembro(unMiembro));
//        Assertions.assertEquals(false,unMiembro.trabajaEnSector(unSector));
//    }
//
//    @Test
//    public void vinculoMiembroConMismoSector() throws SectorException, MiembroException {
//        Organizacion unaOrg = new Organizacion("miRazonSocial",
//                TipoDeOrganizacionEnum.EMPRESA,
//                new ClasificacionOrganizacion("Sector secundario"),
//                new UbicacionGeografica(new Coordenada(10F,5F)));
//
//        Sector unSector = new Sector("miSector",unaOrg);
//        Miembro unMiembro = new Miembro("jose", "pepito",TipoDeDocumento.DNI,12345);
//
//        unMiembro.solicitarIngresoAlSector(unSector);
//
//        Assertions.assertThrows(MiembroException.class,() -> unMiembro.solicitarIngresoAlSector(unSector));
//    }
//
//    @Test
//    public void vinculoVariosMiembros() throws Exception{
//        Organizacion unaOrg = new Organizacion("miRazonSocial",
//                TipoDeOrganizacionEnum.EMPRESA,
//                new ClasificacionOrganizacion("Organizacion"),
//                new UbicacionGeografica(new Coordenada(10F,5F)));
//
//        Sector unSector = new Sector("miSector",unaOrg);
//        Miembro miembro1 = new Miembro("jose", "pepito",TipoDeDocumento.DNI,12345);
//        Miembro miembro2 = new Miembro("pepe", "gomez",TipoDeDocumento.DNI,54321);
//        Miembro miembro3 = new Miembro("mario", "lopez",TipoDeDocumento.DNI,11111);
//
//        Assertions.assertEquals(0,unSector.cantidadMiembros());
//
//        miembro1.solicitarIngresoAlSector(unSector);
//        miembro2.solicitarIngresoAlSector(unSector);
//        miembro3.solicitarIngresoAlSector(unSector);
//
//        Assertions.assertEquals(3,unSector.cantidadMiembros());
//    }

}
