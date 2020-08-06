package com.sap.i40aas.datamanager


import JsonConfig
import identifiables.AssetAdministrationShellEnv
import identifiables.Submodel
import identifiables.Asset
import org.springframework.boot.runApplication
import java.io.File

//TEST kotlin spring application
//@SpringBootApplication
class BlogApplication

fun readFileDirectlyAsText(fileName: String): String = File(fileName).readText(Charsets.UTF_8)


fun main(args: Array<String>) {
  runApplication<BlogApplication>(*args)

  val json = JsonConfig.json

  val sampleJson = readFileDirectlyAsText("src/main/resources/submodel-sample.json")

  val submodelObj = json.parse(Submodel.serializer(),
    sampleJson)

  println(submodelObj.idShort)

  val sampleAASEnvJson = readFileDirectlyAsText("src/main/resources/testAASEnv.json")

  val aasEnvObj = json.parse(AssetAdministrationShellEnv.serializer(),
    sampleAASEnvJson)



  println(aasEnvObj.assets?.get(0)?.idShort?.toString())

}

val sGenerJSON = """{"modelType":{"name":"Property"},"idShort":"devicehealth","parent":{"keys":[{"idType":"IRI","local":true,"type":"Submodel","value":"sap.com/aas/submodels/part-100-device-information-model/10JF-1234-Jf14-PP22"}]},"category":null,"descriptions":[{"language":"EN","text":"NORMAL_0 ;FAILURE_1 ;CHECK_FUNCTION_2 ;OFF_SPEC_3 ;MAINTENANCE_REQUIRED_4 "}],"semanticId":{"keys":[{"idType":"IRI","local":false,"type":"GlobalReference","value":"opcfoundation.org/specifications-unified-architecture/part-100-device-information-model/deviceHealth"}]},"kind":"Instance","embeddedDataSpecifications":[],"qualifiers":null,"valueId":null,"value":"NORMAL_0","valueType":"string"}
"""
