package com.sap.i40aas.datamanager

import JsonConfig
import identifiables.AssetAdministrationShellEnv
import identifiables.Submodel
import kotlinx.serialization.builtins.list

class AASObjectsDeserializer {


    companion object {
        val json = JsonConfig.json


        fun deserializeAASEnv(aasEnvAsSting: String): AssetAdministrationShellEnv {
            return json.parse(AssetAdministrationShellEnv.serializer(),
                    aasEnvAsSting)
        }

        fun deserializeSubmodel(submodelAsString: String): Submodel {
            return json.parse(Submodel.serializer(),
                    submodelAsString)
        }

        fun deserializeSubmodelList(submodelListAsString: String): List<Submodel> {
            return json.parse(Submodel.serializer().list,
                    submodelListAsString)
        }

        fun serializeSubmodelList(submodelsList: List<Submodel>): String {
            return json.stringify(Submodel.serializer().list,
                    submodelsList)
        }

        fun serializeSubmodel(submodel: Submodel): String {
            return json.stringify(Submodel.serializer(),
                    submodel)
        }
    }


}