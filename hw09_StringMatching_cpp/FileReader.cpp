/* 
 * File:   FileReader.cpp
 * Author: Dimitar
 * 
 * Created on June 10, 2015, 12:37 PM
 */

#include "FileReader.hpp"
#include <fstream>
#include <stdexcept>

std::vector<std::string> FileReader::getLines(std::string file) {

    std::ifstream stream(file);
    if (stream.is_open()) {
        std::vector<std::string> lines;
        while (!stream.eof()) {
            std::string line;
            stream >> line;
            lines.push_back(line);
        }
        stream.close();
        return lines;
    } else {
        throw std::invalid_argument("No such file.");
    }
}
