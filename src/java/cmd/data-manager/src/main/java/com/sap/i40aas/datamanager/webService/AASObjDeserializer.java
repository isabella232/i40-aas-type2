//package com.sap.i40aas.datamanager.webService;
//
//import identifiables.AssetAdministrationShellEnv;
//import identifiables.Submodel;
//import utils.JsonConfig;
//
//public class AASObjDeserializer {
//
//  static kotlinx.serialization.json.Json json = JsonConfig.json;
//
//  public static AssetAdministrationShellEnv deserializeAASEnv(String aasEnvAsSting) {
//    return json.parse(AssetAdministrationShellEnv.Companion.serializer(),
//      aasEnvAsSting);
//  }
//
//  https://stackoverflow.com/questions/52971069/ktor-serialize-deserialize-json-with-list-as-root-in-multiplatform
//
//  public static Submodel deserializeSubmodel(String submodelAsString) {
//    return json.parse(Submodel.Companion.serializer().list,
//      json.
//        submodelAsString)
//  }
//
//  fun deserializeSubmodelList(String submodelListAsString):List<Submodel>
//
//  {
//    return json.parse(Submodel.Companion.serializer().list,
//      submodelListAsString)
//  }
//  .class.
//
//  serializer().list
//
//  fun serializeSubmodelList(submodelsList:List<Submodel>):
//
//  String {
//    return json.stringify(Submodel.serializer().list,
//      submodelsList)
//  }
//
//  fun serializeSubmodel(submodel:Submodel):
//
//  String {
//    return json.stringify(Submodel.serializer(),
//      submodel)
//  }
//
//
//}
