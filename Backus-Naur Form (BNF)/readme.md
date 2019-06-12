#BNF.dat file sample:\n
S->abaAC|Ca
A->BaC|B|cC
B->a|b|C|c
C->a|c



#output:
(aba((a|b|(a|c)|c)a(a|c)|(a|b|(a|c)|c)|c(a|c))(a|c)|(a|c)a)


#run command:
java Main /path/to/input/file
