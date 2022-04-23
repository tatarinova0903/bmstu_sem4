#ifndef NODE_H
#define NODE_H

#include <memory>

using namespace std;

template<typename Info>
class Node
{
private:
    Info info;
    shared_ptr<Node<Info>> next;


public:
    Node():
        next(nullptr)
    {}
    
    Node(Info &info):
        info(info),
        next(nullptr)
    {}
    
    ~Node(){}
    
    const Info& get_info() const
    {
        return this->info;
    }
    
    Info& get_info()
    {
        return this->info;
    }
    
    void set_info(const Info &info)
    {
        this->info = info;
    }

    void set_next(shared_ptr<Node<Info>>next)
    {
        this->next = next;
    }

    shared_ptr<Node<Info>> get_next() const
    {
        return next;
    }

};

#endif // NODE_H
