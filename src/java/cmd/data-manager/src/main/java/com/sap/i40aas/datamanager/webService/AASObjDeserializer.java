package com.sap.i40aas.datamanager.webService;

import baseClasses.Reference;
import identifiables.AssetAdministrationShellEnv;
import identifiables.Submodel;
import referables.SubmodelElement;
import utils.JsonConfig;

public class AASObjDeserializer {

  static kotlinx.serialization.json.Json json = JsonConfig.json;

  //Deserialize an AssetAdministrationShellEnv
  public static AssetAdministrationShellEnv deserializeAASEnv(String aasEnvAsSting) {
    return json.parse(AssetAdministrationShellEnv.Companion.serializer(),
      aasEnvAsSting);
  }

  // https://stackoverflow.com/questions/52971069/ktor-serialize-deserialize-json-with-list-as-root-in-multiplatform

  //Deserialize a Submodel
  public static Submodel deserializeSubmodel(String submodelAsString) {
    return json.parse(Submodel.Companion.serializer(), submodelAsString);
  }

  //Serialize a Submodel Element
  public static String serializeSubmodelElement(SubmodelElement element) {
    return json.stringify(SubmodelElement.Companion.serializer(), element);
  }

  public static String serializeReference(Reference ref) {
    return json.stringify(Reference.Companion.serializer(), ref);
  }

//  fun deserializeSubmodelList(String submodelListAsString):List<Submodel>
//
//  {
//    return json.parse(Submodel.Companion.serializer().list,
//      submodelListAsString)
//  }


}
