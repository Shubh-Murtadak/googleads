import 'package:flutter/material.dart';
import 'package:google_mobile_ads/google_mobile_ads.dart';

void main() {
    WidgetsFlutterBinding.ensureInitialized();
  MobileAds.instance.initialize();
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
    late NativeAd _ad;
  bool isLoaded = false;
  
  @override
  void initState() {
    super.initState();
    loadNativeAd();
  }

  @override
  void dispose() {
    _ad.dispose();
    super.dispose();
  }


  void loadNativeAd() {
    _ad = NativeAd(
      request: const AdRequest(),
      adUnitId: "ca-app-pub-3940256099942544/1044960115",
      factoryId: 'fullPage',
      listener: NativeAdListener(
        onAdLoaded: (ad){
          setState(() {
            isLoaded = true;
          });
        },
        onAdFailedToLoad: (ad, error){
          ad.dispose();
          print('failed to load the ad ${error.message}, ${error.code}');
        }
      )
    );

    _ad.load();
  }
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: Builder(
        builder: (context) => Scaffold(
          appBar: AppBar(title: const Text("AppBar"),),
         body:  isLoaded? AdWidget(ad: _ad) : const Center(child:   CircularProgressIndicator()),
         
          bottomNavigationBar: Padding(
            padding:  const EdgeInsets.all(30.0),
            child: ElevatedButton(onPressed: (){
              Navigator.push(
                context, 
                MaterialPageRoute(builder: (context) =>  const SecondRoute())
              );
            }, child: const Text("Open New tab from here")),
          ),
        ),
      )
    );
  }
}

class SecondRoute extends StatelessWidget {
  const SecondRoute({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Second Route'),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: () {
            Navigator.pop(context);
          },
          child: const Text('Go back!'),
        ),
      ),
    );
  }
}
 