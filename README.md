# Barcoder
This is the barcode generation backend service. We use [PartKeepr](https://github.com/partkeepr/PartKeepr) to manage our inventory. Due to its lack of barcode generation feature, this project comes into place.

# Requirements
* JDK 12
* Maven
* Docker

# Compile and run the codes
1. Run `mvn compile` to build source files and run `mvn package` to build Docker image.
2. Execute `docker run barcoder/generation-service:1.0.0` to run this application.
