<h1 align="center"> ThinKarbon </h1> 
<h2 align="center"> Carbon Offsetting Calculator </h2> 
<h2> Structure Explained </h2>
<ul>
  <li>
    <div>
      <h3>UI folder</h3>
      <p>Contains all the activites and fragments</p>
    </div>
  </li>
  <li>
  <div>
      <h3>DAO folder</h3>
      <p>Data access objects, communicates with database</p>
    </div>
  </li>
  <li>
    <div>
      <h3>Model folder</h3>
      <p>Contains all the enitites and interfaces: EmissionService, CarbonEmission, Emission, etc.</p>
      <p>Decorator pattern under the decorator sub-directory</p>
      <p>Emission decorator factory class</p>
      <p>Also contains the service used to capture coordinates under the service sub-directory</p>
    </div>
  </li>
  <li>
    <div>
      <h3>Impl folder</h3>
      <p>Contains the implementaion of the EmissionService interface.</p>
    </div>
  </li>
    <li>
    <div>
      <h3>Rep folder</h3>
      <p>Contains the repository layer. The repository is an extra layer on top of the dao</p>
    </div>
  </li>
      <li>
    <div>
      <h3>DB folder</h3>
      <p>Database setup class.</p>
    </div>
  </li>
</ul>

<h2> Build </h2>
<p> Use Android Studio to build the apk and run using an  <a href="https://developer.android.com/studio/run/emulator" >emulator</a> or a pyshical android device by connecting it through USB</p> 

<h2> Screenshots </h2>

Main Screen                |  Transport Fragment
:-------------------------:|:-------------------------:
![screenshot](https://github.com/oliva20/thinkarbon_app/blob/master/app-screenshots/4.png)   |  ![screenshot](https://github.com/oliva20/thinkarbon_app/blob/master/app-screenshots/3.png) 

