package com.kappdev.wordbook.feature_dictionary.domain.use_cases

data class DictionaryUseCases (
    val addSet: AddSet,
    val insertTerm: InsertTerm,
    val removeSet: RemoveSet,
    val removeTerm: RemoveTerm,
    val updateSet: UpdateSet,
    val getAllSets: GetAllSets,
    val getTermsListBySetId: GetTermsListBySetId,
    val getTermsFlowBySetId: GetTermsFlowBySetId,
    val getTermById: GetTermById,
    val getSetById: GetSetById,
    val getImageFromStorage: GetImageFromStorage,
    val getImageFromUrl: GetImageFromUrl,
    val proposeTermMeaning: ProposeTermMeaning,
    val clearTable: ClearTable,
    val moveTermToSet: MoveTermToSet,
    val getTermsByListOfSetsIds: GetTermsByListOfSetsIds
)