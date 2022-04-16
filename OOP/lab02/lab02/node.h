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
    
    Node(Info &key):
        info(key),
        next(nullptr)
    {}
    
    ~Node(){}
    
    const Info& get_obj() const
    {
        return this->info;
    }
    
    Info& get_obj()
    {
        return this->info;
    }
    
    void set_obj(const Info &key)
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
