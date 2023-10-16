# MyBlogApp

MyBlogApp es una aplicación de Android que permite a los usuarios crear y guardar publicaciones en un blog. La aplicación está desarrollada en Kotlin y sigue la arquitectura de MVVM (Modelo-Vista-VistaModelo).

## Características

- **Crear nuevas publicaciones**: Los usuarios pueden crear nuevas publicaciones proporcionando un título y contenido para la publicación.
- **Validación de entrada**: La aplicación realiza validaciones en tiempo real para garantizar que el título no supere los 15 caracteres y que el contenido no supere los 255 caracteres.
- **Guardar publicaciones**: Una vez que los usuarios completan la información de la publicación, pueden guardarla y se notificará a través de un listener cuando se haya guardado correctamente.
- **Interfaz de usuario intuitiva**: La interfaz de usuario está diseñada utilizando XML para el desarrollo de vistas, siguiendo los principios de Material Design para ofrecer una experiencia de usuario moderna y coherente.
- **Arquitectura MVVM**: La aplicación sigue la arquitectura MVVM, lo que permite una separación clara de responsabilidades y facilita el mantenimiento y la escalabilidad del código.
- **Firebase Firestore**: Se utiliza Firebase Firestore como base de datos en la nube para almacenar y sincronizar las publicaciones de los usuarios.
- **Firebase Authentication**: Se utiliza Firebase Authentication para permitir a los usuarios autenticarse en la aplicación utilizando su correo electrónico y contraseña.
- **Comprobación de sesiones**: La aplicación realiza comprobaciones de sesiones para garantizar que solo los usuarios autenticados puedan acceder y crear publicaciones.
- **Shared Preferences**: Se utiliza Shared Preferences para almacenar información localmente, como el estado de la sesión del usuario.

## Tecnologías utilizadas

- Kotlin: El lenguaje de programación principal utilizado para desarrollar la aplicación.
- Android Jetpack: Se utiliza el componente ViewModel de Android Jetpack para implementar la arquitectura MVVM y gestionar el ciclo de vida de las actividades y fragmentos.
- Data Binding: Se utiliza Data Binding para enlazar los datos del modelo de vista con la interfaz de usuario de manera declarativa.
- XML: Se utiliza XML para el desarrollo de vistas y definir la interfaz de usuario de la aplicación.
- Firebase Firestore: Se utiliza Firebase Firestore como base de datos en la nube para almacenar y sincronizar las publicaciones de los usuarios.
- Firebase Authentication: Se utiliza Firebase Authentication para permitir a los usuarios autenticarse en la aplicación utilizando su correo electrónico y contraseña.
- Shared Preferences: Se utiliza Shared Preferences para almacenar información localmente, como el estado de la sesión del usuario.
- BottomSheetDialogFragment: Se utiliza BottomSheetDialogFragment para mostrar el fragmento de creación de nuevas publicaciones en la parte inferior de la pantalla.
- Material Design: La aplicación sigue los principios de Material Design para ofrecer una experiencia de usuario moderna y coherente.

## Requisitos de instalación

- Android Studio: Se requiere Android Studio para compilar y ejecutar la aplicación en un emulador o dispositivo Android.
- Dispositivo Android o Emulador: Se requiere un dispositivo Android o un emulador para ejecutar la aplicación.

## Instrucciones de compilación

1. Clona el repositorio de MyBlogApp en tu máquina local.
2. Abre Android Studio y selecciona "Open an existing Android Studio project".
3. Navega hasta la ubicación donde clonaste el repositorio y selecciona la carpeta del proyecto.
4. Espera a que Android Studio importe y construya el proyecto.
5. Conecta un dispositivo Android o inicia un emulador.
6. Haz clic en el botón "Run" en Android Studio para compilar y ejecutar la aplicación en el dispositivo o emulador seleccionado.

## Contribución

Si deseas contribuir a MyBlogApp, sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una rama con una descripción clara de la función o corrección que deseas realizar.
3. Realiza los cambios en tu rama y realiza commits con mensajes descriptivos.
4. Haz un push de tus cambios a tu repositorio fork.
5. Abre un pull request en el repositorio principal, describiendo los cambios realizados.



## Contacto

Si tienes alguna pregunta o sugerencia sobre MyBlogApp, no dudes en ponerte en contacto con nosotros en pmpedrotorres@gmail.com. Estaremos encantados de ayudarte.
