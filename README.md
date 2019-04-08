# Projet PRE

## Compilation et exécution

Pour compiler le projet entier, utiliser la commande `./compile.sh`.
Sous windows, utiliser `wincompile`.

Pour exécuter le projet, utiliser la commande `java app.core.Main`.

Il est nécessaire d'utiliser la commande `. ./classpath.sh` ou
`export CLASSPATH=bin:lib/swingx-all-1.6.4.jar` avant la première exécution.

Attention : le main principal n'est pas exécutable sur c9 car il utiliser
l'interface graphique, il faut donc télécharger le projet et l'exécuter sur
votre machine.

Vous pouvez cependant exécuter les tests unitaires avec la commande
`java test.nomClasse.java`.

## Connexion à la base de données

Pour se connecter à la base de données, utiliser la commande `mysql -u mvienne`.
phpmyadmin est accessible à l'adresse : https://pre-mvienne.c9users.io/phpmyadmin
avec le même nom d'utilisateur:  mvienne
et un blank password.

## Charte de présentation du code

Utiliser des indentations de 4 espaces et des accolades ouvrantes à la ligne
comme dans le code suivant :

```java
package test;

public class Test
{
    public static void main(String[] args)
    {
        boolean test = true; // condition débile juste pour l'exemple
        do
        {
            System.out.println("Test");
            test = false
        }
        while (test = true);
    }
}
```

Espacer le code (entre les '=' par exemple).

C'est important d'être consistant dans la charte de code.

## Documentation

Lire `doc/documentation.md` avant de commencer à coder.