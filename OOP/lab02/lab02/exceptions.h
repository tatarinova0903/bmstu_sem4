#ifndef EXCEPTIONS_H
#define EXCEPTIONS_H

#include <exception>
#include <string>

using namespace std;

class base_exception : public exception
{
public:
    base_exception(string filename, string classname, int line, const char *time, string info)
    {
        err_info = "filename: " + filename + "\nclass name: " + classname + "\nline: " + to_string(line)
                   + "\ntime: " + time + "\ninfo: " + info;
    }
    virtual string what(void) {return this->err_info;}
protected:
    string err_info;
};

class memory_allocate_exception : public base_exception
{
public:
    memory_allocate_exception(string filename, string classname, int line, const char *time, string info)
    : base_exception(filename, classname, line, time, info) {}
    string what(void) {return this->err_info;}
};

class iterator_exception : public base_exception
{
public:
    iterator_exception(string filename, string classname, int line, const char *time, string info)
            : base_exception(filename, classname, line, time, info) {}
    string what(void) {return this->err_info;}
};

#endif // EXCEPTIONS_H
