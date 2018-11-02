package com.example.hossam.motion.adapters

/*
class MyViewPagerAdapter(private val context: Context, private val myObject: MyObject) : PagerAdapter() {
    private val images: List<Image>

    val count: Int
        get() = this.images.size()

    init {
        this.images = myObject.getImages()
    }

    fun instantiateItem( collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val imageUrl = Media.getImageUrl(myObject.getObjectId(), images[position].getImageId())
        val layout = inflater.inflate(R.layout.object_details_image, collection, false) as ViewGroup
        val pagerImage = layout.findViewById(R.id.pagerImage)
        Media.setImageFromUrl(pagerImage, imageUrl)//call to GlideApp or Picasso to load the image into the ImageView
        collection.addView(layout)
        return layout
    }

    fun destroyItem( container: ViewGroup, position: Int,  view: Any) {
        container.removeView(view as View)
    }

    fun isViewFromObject( view: View,  `object`: Any): Boolean {
        return view === `object`
    }
}*/
