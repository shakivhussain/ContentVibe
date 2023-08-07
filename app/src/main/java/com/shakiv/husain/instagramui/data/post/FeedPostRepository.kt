package com.shakiv.husain.instagramui.data.post

import com.shakiv.husain.instagramui.data.LocalPostProvider
import com.shakiv.husain.instagramui.data.Resource
import com.shakiv.husain.instagramui.data.model.PostFeed
import com.shakiv.husain.instagramui.domain.service.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedPostRepository @Inject constructor() : PostRepository {

    private var requestCount = 0

    override suspend fun getPostFeed(): Resource<PostFeed> {
        return withContext(Dispatchers.IO){
            delay(2_000)
            if (shouldRandomlyFail()){
                Resource.Error(IllegalStateException())
            }else{
                Resource.Success(LocalPostProvider.getFeed())
            }
        }
    }


    private fun shouldRandomlyFail(): Boolean = ++ requestCount % 5 == 0


}