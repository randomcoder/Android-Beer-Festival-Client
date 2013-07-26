package uk.co.randomcoding.android.beerfestival

import _root_.android.app.Activity
import _root_.android.os.Bundle

class MainActivityOld extends Activity with TypedActivity {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)

    findView(TR.textview).setText("hello, world!")
  }
}
