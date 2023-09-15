package com.shakiv.husain.contentvibe.data.post

import com.shakiv.husain.contentvibe.domain.repository.PostRepository
import javax.inject.Inject

class FeedPostRepository @Inject constructor() : PostRepository {

    private var requestCount = 0

//    override suspend fun getPostFeed(): Resource<PostFeed> {
//        return withContext(Dispatchers.IO){
//            delay(2_000)
//            if (shouldRandomlyFail()){
//                Resource.Error(IllegalStateException())
//            }else{
//                Resource.Success(LocalPostProvider.getFeed())
//            }
//        }
//    }


    private fun shouldRandomlyFail(): Boolean = ++ requestCount % 5 == 0


}