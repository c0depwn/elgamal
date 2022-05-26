# El Gamal CLI

This is an oversimplified implementation of the ElGamal encryption algorithm.
This tool should not be used in any meaningful real-world way and is simply an implementation to gain better understanding on how the ElGamal procedure works.

## Usage
To get an idea of what you can do with this tool try to run the `help` command.

## Encrypting
```shell
encrypt -p <PUBLIC_KEY> -i <TEXT_FILE> -o <ENCRYPTED_FILE>
```

## Decrypting
```shell
decrypt -p <PRIVATE_KEY> -i <ENCRYPTED_FILE> -o <DECRYPTED_FILE> 
```

## Key Pair Generation
`generate` will create a new key pair and store the private key (`sk.txt`) and the public key (`pk.txt`) inside the specified directory.

```shell
generate -o <OUTPUT_DIR>
```

## Assignment & Running with Maven (mvn)

Decrypt `chiffre.txt` using `sk.txt` and write result to `text.txt`.

```shell
mvn compile exec:java -Dexec.mainClass=ch.fhnw.mada.Main -Dexec.args="decrypt -p sk.txt -i chiffre.txt -o text-d.txt"
```

## Ensure encryption works with newly generated keys

1. Generate a new keypair
```shell
mvn compile exec:java -Dexec.mainClass=ch.fhnw.mada.Main -Dexec.args="generate -o ./ -p test-"
```
2. Encrypt `original.txt` using generated `test-pk.txt`
```shell
mvn compile exec:java -Dexec.mainClass=ch.fhnw.mada.Main -Dexec.args="encrypt -p test-pk.txt -i original.txt -o encrypted.txt"
```
3. Decrypt `encrypted.txt` using generated `test-sk.txt` and write result to `decrypted.txt`
```shell
mvn compile exec:java -Dexec.mainClass=ch.fhnw.mada.Main -Dexec.args="decrypt -p test-sk.txt -i encrypted.txt -o decrypted.txt"
```
Now `original.txt` and `decrypted.txt` should match meaning that the whole procedure works. 
This can be checked manually or on a UNIX system you can use the `diff` command.
```shell
diff original.txt decrypted.txt
```

