> 写在开始，题目大部分参考[这里](http://hebin.me/2017/09/01/%E8%A5%BF%E6%99%AE%E5%AE%9E%E9%AA%8C%E5%90%A7ctf%E8%A7%A3%E9%A2%98writeup%E5%A4%A7%E5%85%A8/)的wp，感谢博客主

# tmp.rar
解压获得.class文件 用jd-guid打开
复制代码运行，详见[test1.java](\code\test1.java)

# ABCTF Frozen Recursion - Reverse Engineering 250 Writeup
转自[这里](http://maroueneboubakri.blogspot.com/2016/07/abctf-frozen-recursion-reverse.html)
```bash
# gdb recursive_python
gdb-peda$ break chmod
gdb-peda$ r
gdb-peda$ shell
#chmod +x unstep_84fc2d39
#gdb unstep_84fc2d39
gdb-peda$ b chmod
gdb-peda$ r
gdb-peda$ shell
# chmod +x unstep_34a4d33b
# gdb unstep_34a4d33b
gdb-peda$ break chmod
gdb-peda$ r
gdb-peda$ shell
#chmod +x unstep_579c82e9
#gdb unstep_579c82e9
gdb-peda$ break chmod
gdb-peda$ r
gdb-peda$ shell
# chmod +x unstep_f67baaeb
# gdb unstep_f67baaeb
gdb-peda$ start
gdb-peda$ find flag
unstep_f67baaeb : 0x8693c3 ("flag{python_taken_2_far}s\032")
```
最后一个步骤start的时候,我运行报错，然后换成
```bash
strings unstep_f67baaeb | grep -o 'flag{.*}'
```
The flag is flag{python_taken_2_far}

# whatamitoyou

这题对于我这看不懂汇编的小白来说好难
完整的输入为CBDABCADBCCABBABBABACBCCABDADBABABB
```bash
./whatamitoyou CBDABCADBCCABBABBABACBCCABDADBABABB
```
然后获得Your password is tjctf{pblgjd}

参见[这里](https://blog.csdn.net/wangtiankuo/article/details/80716782)或者[这里](http://sibears.ru/labs/TJCTF-2016-whatamitoyou/)


# py137
这道题涉及到pyc文件的字节码，手动分析字节码，暂时还没有学到手。。。
参考[这里](http://hebin.me/2018/01/24/%E8%A5%BF%E6%99%AEctf-py137/)

# pinstore

简单的apk逆向没有加壳，没有.so文件，单纯的java加密+读取sqlite3
代码参考[这里](\code\depinstorre.java)

# debug32
linux 32位elf下的可执行程序 
直接./debug32 报错 ./debug32: Permission denied
```bash
$ chmod 755 debug32
$ ./debug32

./debug32:No such file or directory
```
再次 报错
```bash
$ file debug32 
debug32: ELF 32-bit LSB executable, Intel 80386, version 1 (SYSV), dynamically linked, interpreter /lib/ld-linux.so.2, for GNU/Linux 2.6.32, BuildID[sha1]=46b87dea3b50c74ec8cde885ccc9a4d9e5e260f3, stripped
```
32位elf 修改兼容性然后再次运行！！！！
直接运行没有任何反应

I looked through the assembly in IDA and saw “Printing Flag” being printed somewhere.
So the first and probably the last thing I needed to do was to jump to the function printing it. The address of the function as we can see is at 0x804849B.

For this I used gdb. My first instinct was to set a breakpoint at main, then set the eip to the address of the required function and continue. This would print out the flag.

But since this was a stripped binary(hence no symbols table), it didn’t recognise main as a valid breakpoint. So I set the breakpoint at __libc_start_main() function. This is the function which sets up the environment and then calls the main() function when the binary is run.

So to carry out the required task, these were the commands I used:
```bash
break __libc_start_main
set $eip = 0x804849b
continue
```
result:
```bash
(gdb) b __libc_start_main
Breakpoint 1 at 0x8048370
(gdb) r
Starting program: /root/桌面/debug32 

Breakpoint 1, 0xf7defd90 in __libc_start_main () from /lib32/libc.so.6
(gdb) set $eip = 0x804849b
(gdb) c
Continuing.
Printing flag
i_has_debugger_skill

Program received signal SIGSEGV, Segmentation fault.
0x080483c1 in ?? ()

```
This as expected printed out the flag!