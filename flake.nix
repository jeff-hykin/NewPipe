{
  description = "NewPipe Android build environment";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs {
          inherit system;
          config = {
            allowUnfree = true;
            android_sdk.accept_license = true;
          };
        };

        androidComposition = pkgs.androidenv.composeAndroidPackages {
          buildToolsVersions = [ "35.0.0" ];
          platformVersions = [ "36" "35" ];
          includeEmulator = false;
          includeNDK = false;
          includeSources = false;
          includeSystemImages = false;
        };

        androidSdk = androidComposition.androidsdk;
      in
      {
        devShells.default = pkgs.mkShell {
          buildInputs = [
            pkgs.jdk17
            pkgs.jdk21
            androidSdk
            pkgs.gradle
          ];

          ANDROID_HOME = "${androidSdk}/libexec/android-sdk";
          ANDROID_SDK_ROOT = "${androidSdk}/libexec/android-sdk";
          JAVA_HOME = "${pkgs.jdk17.home}";
          JDK17_HOME = "${pkgs.jdk17.home}";
          JDK21_HOME = "${pkgs.jdk21.home}";

          shellHook = ''
            echo "NewPipe build environment ready"
            echo "JAVA_HOME=$JAVA_HOME"
            echo "ANDROID_HOME=$ANDROID_HOME"
            # Ensure Gradle can find both JDK 17 and 21 for toolchain resolution
            if ! grep -q 'org.gradle.java.installations.paths' gradle.properties 2>/dev/null; then
              echo "org.gradle.java.installations.paths=$JDK17_HOME,$JDK21_HOME" >> gradle.properties
            fi
          '';
        };
      }
    );
}
