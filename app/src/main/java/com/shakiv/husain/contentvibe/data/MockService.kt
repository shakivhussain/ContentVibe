package com.shakiv.husain.contentvibe.data

import com.shakiv.husain.contentvibe.data.model.UserEntity
import com.shakiv.husain.contentvibe.domain.model.BottomSheetItem

object MockService {
    fun getBottomSheetItems(): List<BottomSheetItem> {
        return listOf(
//            BottomSheetItem.HIDE,
            BottomSheetItem.REPORT,
            BottomSheetItem.DELETE
        )
    }

    fun getMockStoryItem(): StoryItem {

        val story = StoryItem(
            id = "0CQvrRGuqRVrFwg0X7lJ", user = UserEntity(
                userId = "sK3ThI5fxoTtUMtpuRI6bgpmXkW2", isAnonymous = true,
                userName = "Shakib Mansoori", userAbout ="", email = "shakibraza436@gmail.com",
                profileUrl = "https://lh3.googleusercontent.com/a/ACg8ocLMO5NvCcQ1kdFEtXnVEMCawT_nM-ZmxtJNSMvPbVG2ICA=s96-c",
                createdAt = "2023-09-25T11:14:26.160Z",
                description = "Your time is limited, don't waste it living someone else's life."
            ),
            storyImage = "https://firebasestorage.googleapis.com/v0/b/contentvibe-f9adc.appspot.com/o/images%2Fef0759c4-55e7-46cf-a581-d6213e431173.jpg?alt=media&token=ce263257-0b4c-44ab-8a67-f243691de5bd",
            publishAt = 1696839920678, expireAt = 1696926320678, isViewed = true
        )

        return story
    }
}