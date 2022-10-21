package com.example.testroomkotlin.db.model

import java.io.Serializable

class ContentModel (
    var id: Int? = null,
    var text: String? = null,
    var isCheck: Boolean? = null
): Serializable