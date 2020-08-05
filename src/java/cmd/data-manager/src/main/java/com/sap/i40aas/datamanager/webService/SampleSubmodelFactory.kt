package com.sap.i40aas.datamanager.webService

import baseClasses.*
import identifiables.Submodel
import referables.ISubmodelElement
import referables.Property
import types.*

class SampleSubmodelFactory {

    companion object {
        fun getSampleSubmodel(_idShort: String): Submodel {
            val idShort = _idShort
            val description = arrayListOf<LangString>(
                    LangString("en-us",
                            "An example asset identification submodel for the test application")
                    ,
                    LangString(
                            "de",
                            "Ein Beispiel-Identifikations-Submodel f\u00fcr eine Test-Anwendung"
                    )
            )

            val modelType = ModelType(ModelTypesEnum.Submodel)
            val identification =
                    Identifier("http://acplt.org/Submodels/Assets/TestAsset/Identification",
                            idType = IdTypeEnum.IRI)
            val administration = AdministrativeInformation("8.9",
                    "0")
            val semanticId = Reference(
                    arrayListOf(
                            Key(
                                    IdTypeEnum.IRDI,
                                    false,
                                    KeyElementsEnum.GlobalReference,
                                    "http://acplt.org/SubmodelTemplates/AssetIdentification"
                            )
                    )
            )

            val qualifier = Qualifier(
                    "http://acplt.org/Qualifier/ExampleQualifier",
                    null,
                    "100",
                    Reference(arrayListOf<Key>(Key(IdTypeEnum.IRDI,
                            false,
                            KeyElementsEnum.GlobalReference,
                            "http://acplt.org/ValueId/ExampleValueId"))),
                    ValueTypeEnum.string

            )

            val propertyElem1 = Property(
                    "ManufacturerName",
                    Reference(
                            arrayListOf(
                                    Key(
                                            IdTypeEnum.IRDI,
                                            false,
                                            KeyElementsEnum.GlobalReference,
                                            "http://acplt.org/SubmodelTemplates/AssetIdentification"
                                    )
                            )
                    ),
                    KindEnum.Instance,
                    arrayListOf(),
                    arrayListOf(qualifier),
                    arrayListOf<LangString>(
                            LangString(
                                    "en-us",
                                    "Legally valid designation of the natural or judicial person which is directly responsible for the design, production, packaging and labeling of a product in respect to its being brought into circulation."
                            )
                            ,
                            LangString(
                                    "de",
                                    "Ein Beispiel-Identifikations-Submodel f\u00fcr eine Test-Anwendung"
                            )
                    ),
                    null,
                    null,
                    Reference(
                            arrayListOf<Key>(
                                    Key(
                                            IdTypeEnum.IRDI,
                                            false,
                                            KeyElementsEnum.GlobalReference,
                                            "http://acplt.org/ValueId/ExampleValueId"
                                    )
                            )
                    ),
                    "ACPLT",
                    ValueTypeEnum.string
            )


            val submodelElements = arrayListOf<ISubmodelElement>(
                    propertyElem1
            )

            //create a submodel obj
            val submodelObj = Submodel(
                    idShort,
                    identification,
                    semanticId,
                    null,
                    null,
                    null,
                    description,
                    null,
                    null,
                    administration,
                    submodelElements
            )


            return submodelObj
        }
    }

}